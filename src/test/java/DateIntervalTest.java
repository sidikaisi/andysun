import arithmetic.intervaltree.DateInterval;
import arithmetic.intervaltree.Interval;
import arithmetic.intervaltree.IntervalTree;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import static org.junit.Assert.*;

/**
 *  @Description
 *  @Author zhangxiaojun
 *  @Date 2018/10/10
 *  @Version 1.0.0
 **/
public class DateIntervalTest {
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

	@Test
	public void test_middlepointBounded() {
		assertEquals(parse("12.11.2005 22:20:15"),
				new DateInterval(parse("31.10.2005 10:20:15"), parse("25.11.2005 10:20:15"), Interval.Bounded.CLOSED)
						.getMidpoint());
		assertEquals(parse("12.11.2005 10:20:15"),
				new DateInterval(parse("30.10.2005 10:20:15"), parse("25.11.2005 10:20:15"), Interval.Bounded.CLOSED)
						.getMidpoint());
	}

	@Test
	public void test_middlepointUnbounded() {
		assertEquals(new Date(0), new DateInterval().getMidpoint());
		assertEquals(new Date(4611677853814364403L),
				new DateInterval(new Date(-16329226047000L), Interval.Unbounded.CLOSED_LEFT).getMidpoint());
		assertEquals(new Date(-4611694183040411404L),
				new DateInterval(new Date(-16329226047000L), Interval.Unbounded.CLOSED_RIGHT).getMidpoint());
		assertEquals(new Date(4611677853814364403L),
				new DateInterval(new Date(-16329226047000L), Interval.Unbounded.OPEN_LEFT).getMidpoint());
		assertEquals(new Date(-4611694183040411404L),
				new DateInterval(new Date(-16329226047000L), Interval.Unbounded.OPEN_RIGHT).getMidpoint());
	}

	@Test
	public void test_middlepointStartAndEndOffByOne() {
		long tmp = 128643893;
		assertEquals(new Date(tmp + 1),
				new DateInterval(new Date(tmp), new Date(tmp + 1), Interval.Bounded.CLOSED_RIGHT).getMidpoint());
		assertEquals(new Date(tmp),
				new DateInterval(new Date(tmp), new Date(tmp + 1), Interval.Bounded.CLOSED).getMidpoint());
		assertEquals(new Date(tmp),
				new DateInterval(new Date(tmp), new Date(tmp), Interval.Bounded.CLOSED).getMidpoint());
		assertNull(new DateInterval(new Date(tmp), new Date(tmp), Interval.Bounded.OPEN).getMidpoint());
	}

	@Test
	public void test_dateIntervalTree() {
		IntervalTree<Date> tree = new IntervalTree<>();
		Date g = parse("30.09.2016 08:05:42");
		Date f = parse("18.09.2016 23:22:00");
		Date e = parse("12.09.2016 12:15:00");
		Date d = parse("12.09.2016 12:14:59");
		Date c = parse("01.03.2016 17:39:08");
		Date b = parse("27.12.2015 08:05:42");
		Date a = parse("08.04.2010 08:02:27");

		DateInterval aa = new DateInterval(e, f, Interval.Bounded.CLOSED);
		Interval<Date> bb = aa.builder().greaterEqual(b).less(d).build();
		DateInterval cc = new DateInterval(c, d, Interval.Bounded.CLOSED_RIGHT);
		DateInterval dd = new DateInterval(a, d, Interval.Bounded.OPEN);
		DateInterval ee = new DateInterval(e, g, Interval.Bounded.CLOSED_LEFT);

		tree.add(aa);
		tree.add(bb);
		tree.add(cc);
		tree.add(dd);
		tree.add(ee);

		Set<Interval<Date>> res = tree.query(new DateInterval(c, d, Interval.Bounded.OPEN));
		assertEquals(3, res.size());
		assertTrue(res.contains(bb));
		assertTrue(res.contains(cc));
		assertTrue(res.contains(dd));
	}

	@Test
	public void testDate() {
		IntervalTree<Date> tree = new IntervalTree<>();
		Date s1 = DateUtils.formatStr2Date("2018-09-01 00:00:00", DateUtils.YYYY_MM_DD_HH_MM_SS);
		Date e1 = DateUtils.formatStr2Date("2018-10-03 00:00:00", DateUtils.YYYY_MM_DD_HH_MM_SS);
		Date s2 = DateUtils.formatStr2Date("2018-09-05 00:00:00", DateUtils.YYYY_MM_DD_HH_MM_SS);
		Date e2 = DateUtils.formatStr2Date("2018-11-02 00:00:00", DateUtils.YYYY_MM_DD_HH_MM_SS);
		DateInterval aa = new DateInterval(s1, e1, Interval.Bounded.CLOSED);
		DateInterval bb = new DateInterval(s2, e2, Interval.Bounded.CLOSED);

		Date point = DateUtils.formatStr2Date("2018-10-03 00:00:00", DateUtils.YYYY_MM_DD_HH_MM_SS);
		tree.add(aa);
		tree.add(bb);
		Set<Interval<Date>> query = tree.query(point);
		System.out.println(query.size());
		System.out.println(JSONObject.toJSONString(query));
		System.out.println("===============================");
		DateInterval cc = new DateInterval(s1, e2, Interval.Bounded.CLOSED);
		Set<Interval<Date>> query1 = tree.query(cc);
		System.out.println(query1.size());
		System.out.println(JSONObject.toJSONString(query1));
	}

	private static Date parse(String str) {
		try {
			return sdf.parse(str);
		} catch (Exception e) {
			return null;
		}
	}
}
