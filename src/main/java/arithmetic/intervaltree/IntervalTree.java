package arithmetic.intervaltree;

import java.util.*;

/**
 *  @Description
 *  @Author zhangxiaojun
 *  @Date 2018/10/10
 *  @Version 1.0.0
 **/
public class IntervalTree<T extends Comparable<? super T>> extends AbstractSet<Interval<T>> {

	/**
	 * The root of the current interval tree. It is {@code null} initially, when the tree is
	 * empty and may change as the result of adding or removing intervals to the tree.
	 */
	TreeNode<T> root;

	/**
	 * The size of the interval tree, or the amount of intervals stored in it.
	 */
	int size;

	/**
	 * Adds an interval to the tree. If the interval is empty, it is rejected and not
	 * stored in the tree. This operation may cause a rebalancing of the tree, which
	 * in turn may cause intervals to be {@link TreeNode#assimilateOverlappingIntervals(TreeNode) assimilated}.
	 * This is why this operation may run in {@code O(n)} worst-case time, even though
	 * on average it should run in {@code O(logn)} due to the nature binary trees.
	 *
	 * @param interval The interval to be added to the tree.
	 * @return {@code true}, if the tree has been modified as a result of the operation,
	 *         or {@code false} otherwise.
	 */
	@Override
	public boolean add(Interval<T> interval) {
		if (interval.isEmpty())
			return false;
		int sizeBeforeOperation = size;
		root = TreeNode.addInterval(this, root, interval);
		return size == sizeBeforeOperation;
	}

	/**
	 * Searches for and returns all intervals stored in the tree, that contain a given
	 * query point. This operation is guaranteed to run in {@code O(logn + k)}, where
	 * {@code n} is the size of the tree and {@code k} is the size of the returned set,
	 * provided that the time complexity of iterating over the intervals stored in each
	 * visited node is amortized {@code O(1)}. This assumption is met for the current
	 * implementation of {@link TreeNode}, where {@link TreeSet}s are used.
	 *
	 * @param point The query point.
	 * @return A set containing all intervals from the tree, intersecting the query point.
	 */
	public Set<Interval<T>> query(T point) {
		return TreeNode.query(root, point, new HashSet<Interval<T>>());
	}

	/**
	 * Searches for and returns all intervals stored in the tree, that intersect a given
	 * query interval. This operation is guaranteed to run in {@code O(logn + k)}, where
	 * {@code n} is the size of the tree and {@code k} is the size of the returned set,
	 * provided that the time complexity of iterating over the intervals stored in each
	 * visited node is amortized {@code O(1)}. This assumption is met for the current
	 * implementation of {@link TreeNode}, where {@link TreeSet}s are used.
	 *
	 * @param interval The query interval.
	 * @return A set containing all intervals from the tree, intersecting the query interval.
	 */
	public Set<Interval<T>> query(Interval<T> interval) {
		Set<Interval<T>> result = new HashSet<>();

		if (root == null || interval.isEmpty())
			return result;
		TreeNode<T> node = root;
		while (node != null) {
			if (interval.contains(node.midpoint)) {
				result.addAll(node.increasing);
				TreeNode.rangeQueryLeft(node.left, interval, result);
				TreeNode.rangeQueryRight(node.right, interval, result);
				break;
			}
			if (interval.isLeftOf(node.midpoint)) {
				for (Interval<T> next : node.increasing) {
					if (!interval.intersects(next))
						break;
					result.add(next);
				}
				node = node.left;
			} else {
				for (Interval<T> next : node.decreasing) {
					if (!interval.intersects(next))
						break;
					result.add(next);
				}
				node = node.right;
			}
		}
		return result;
	}

	/**
	 * Removes an interval from the tree, if it was stored in it. This operation may cause the
	 * {@link TreeNode#deleteNode(TreeNode) deletion of a node}, which in turn may cause
	 * rebalancing of the tree and the {@link TreeNode#assimilateOverlappingIntervals(TreeNode) assimilation}
	 * of intervals from one node to another. This is why this operation may run in {@code O(n)}
	 * worst-case time, even though on average it should run in {@code O(logn)} due to the
	 * nature binary trees.
	 *
	 * @param interval
	 * @return
	 */
	public boolean remove(Interval<T> interval) {
		if (interval.isEmpty() || root == null)
			return false;
		int sizeBeforeOperation = size;
		root = TreeNode.removeInterval(this, root, interval);
		return size == sizeBeforeOperation;
	}


	// =========================================================================
	// ============== Iterator over the Intervals in the tree ==================
	// =========================================================================

	@Override
	public Iterator<Interval<T>> iterator() {
		if (root == null) {
			return Collections.emptyIterator();
		} else {
			final TreeNode.TreeNodeIterator it = root.iterator();
			return new Iterator<Interval<T>>() {
				@Override
				public void remove() {
					if (it.currentNode.increasing.size() == 1) {
						root = TreeNode.removeInterval(IntervalTree.this, root, it.currentInterval);

						// Rebuild the whole branch stack in the iterator, because we might have
						// moved nodes around and introduced new nodes into the branch. The rule
						// is, add all nodes to the branch stack, to which the current node is
						// a left descendant.
						TreeNode<T> node = root;
						it.stack = new Stack<>();

						// Continue pushing elements according to the aforementioned rule until
						// you reach the subtreeRoot - this is the root of the subtree, which
						// the iterator has marked for traversal next. This subtree must not
						// become a part of the branch stack, or otherwise you will iterate over
						// some intervals twice.
						while (node != it.subtreeRoot) {
							if (it.currentNode.midpoint.compareTo(node.midpoint) < 0) {
								it.stack.push(node);
								node = node.left;
							} else {
								node = node.right;
							}
						}
					} else {
						it.remove();
					}
				}

				@Override
				public boolean hasNext() {
					return it.hasNext();
				}

				@Override
				public Interval<T> next() {
					return it.next();
				}
			};
		}
	}


	// =========================================================================
	// ================== Methods from the Set interface =======================
	// =========================================================================

	/**
	 * Returns the size of the tree.
	 *
	 * @return The amount of intervals, stored in the tree.
	 */
	public int size() {
		return size;
	}

	/**
	 * Removes all intervals from the tree. This is an {@code O(1)} worst-case
	 * time operation.
	 */
	@Override
	public void clear() {
		size = 0;
		root = null;
	}

	/**
	 * Checks if a given object is stored in the tree. This method uses binary
	 * search instead of iteration over all intervals, which is why it runs in
	 * guaranteed {@code O(logn)} worst-case time.
	 * @param o The query object.
	 * @return {@code true}, if the object is stored in the tree, or {@code false}
	 *         otherwise.
	 */
	@Override
	public boolean contains(Object o) {
		if (root == null || o == null)
			return false;
		if (!(o instanceof Interval))
			return false;
		Interval<T> query;
		query = (Interval<T>) o;
		TreeNode<T> node = root;
		while (node != null) {
			if (query.contains(node.midpoint)) {
				return node.increasing.contains(query);
			}
			if (query.isLeftOf(node.midpoint)) {
				node = node.left;
			} else {
				node = node.right;
			}
		}

		return false;
	}
}