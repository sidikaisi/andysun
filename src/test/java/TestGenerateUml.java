import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

/**
 *  @Description
 *  CREATE TABLE `table_uml` (
 *   `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
 *   `start_table` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '来源表',
 *   `start_table_attr` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '来源表属性',
 *   `end_table` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '目的表',
 *   `end_table_attr` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '目的表的属性',
 *   `line_comment` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '线的备注',
 *   PRIMARY KEY (`id`)
 * ) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
 *  @Author zhangxiaojun
 *  @Date 2018/10/25
 *  @Version 1.0.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TestGenerateUml {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ExecutorService myPool;

	@Test
	public void generate() {
		List<Map<String, Object>> tables = jdbcTemplate.queryForList("show tables");
		List<Map<String, Object>> tableUmlsMap = jdbcTemplate.queryForList("select * from table_uml");
		List<TableUml> tableUmls = initUml(tableUmlsMap);
		Set<String> relationTables = new HashSet<>();
		Set<String> startTableAttrMap = new HashSet<>();
		tableUmls.forEach(t -> {
			relationTables.add(t.getStartTabLe());
			relationTables.add(t.getEndTabLe());
			startTableAttrMap.add(t.getStartTabLe() + "-" + t.getStartTabLeAttr());
		});
		Vector<String> nodes = new Vector();
		tables = tables.stream().filter(t -> relationTables.contains(t.get("Tables_in_t1").toString()))
				.collect(Collectors.toList());
		int size = tables.size();
		int step = 5;
		if (size <= step) {
			commonExecInitNode(tables, nodes, startTableAttrMap);
		} else {
			if (size > 100) {
				step = 10;
			}
			int count = size / step;
			if (count * step != size) {
				count += 1;
			}
			CountDownLatch countDownLatch = new CountDownLatch(count);
			for (int i = 0; i < count; i++) {
				int end = (i + 1) * step > size ? size : (i + 1) * step;
				execInitNodes(tables.subList(i * step, end), nodes, startTableAttrMap, countDownLatch);
			}
			try {
				countDownLatch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		new DrawUML(nodes, tableUmls);
	}

	private void execInitNodes(List<Map<String, Object>> tables, Vector<String> nodes, Set<String> startTableAttrMap,
			CountDownLatch countDownLatch) {
		myPool.execute(() -> {
			commonExecInitNode(tables, nodes, startTableAttrMap);
			countDownLatch.countDown();
		});
	}

	private void commonExecInitNode(List<Map<String, Object>> tables, Vector<String> nodes, Set<String> startTableAttrMap){
		tables.forEach(t -> {
			String tableName = t.get("Tables_in_t1").toString();
			List<Map<String, Object>> maps = jdbcTemplate.queryForList(
					"select  column_name, column_type,column_comment from information_schema.columns where table_schema ='t1'  and table_name = '"
							+ tableName + "' ");
			nodes.add(getNodeStr(maps, tableName, startTableAttrMap));
		});
	}

	private List<TableUml> initUml(List<Map<String, Object>> tableUmlsMap) {
		List<TableUml> list = new ArrayList<>();
		tableUmlsMap.forEach(t -> {
			TableUml uml = new TableUml();
			uml.setId(Long.parseLong(t.get("id").toString()));
			uml.setStartTabLe(t.get("start_table").toString());
			uml.setStartTabLeAttr(t.get("start_table_attr").toString());
			uml.setEndTabLe(t.get("end_table").toString());
			uml.setEndTabLeAttr(t.get("end_table_attr").toString());
			uml.setLineComment(t.get("line_comment").toString());
			list.add(uml);
		});

		return list;
	}

	private String getNodeStr(List<Map<String, Object>> maps, String tableName, Set<String> startTableAttrMap) {
		List<Map<String, Object>> tableComments = jdbcTemplate.queryForList(
				"select table_name,table_comment from information_schema.tables where table_schema='t1' and table_name='"
						+ tableName + "'");
		String tableComment = tableComments.get(0).get("table_comment").toString();
		StringBuilder s = new StringBuilder();
		s.append(tableName)
				.append(" [label=<<table cellspacing =\"0\"><tr><td colspan=\"3\">" + tableName + "(" + tableComment
						+ ")</td></tr>");
		maps.forEach(a -> {
			String columnName = a.get("column_name").toString();
			String columnType = a.get("column_type").toString();
			String columnComment = a.get("column_comment").toString();
			columnComment = columnComment.replaceAll("\\|", "//");
			columnComment = columnComment.replaceAll("->", "~");
			if (startTableAttrMap.contains(tableName + "-" + columnName)) {
				initOutKeyAttr(s, columnName, columnType, columnComment);
			} else {
				initCommonKeyAttr(s, columnName, columnType, columnComment);
			}
		});
		s.append("</table>>];");
		return s.toString();
	}

	private void initCommonKeyAttr(StringBuilder s, String columnName, String columnType, String columnComment) {
		s.append("<tr><td>" + columnName + "</td>");
		s.append("<td>" + columnType + "</td>");
		s.append("<td>" + columnComment + "</td></tr>");
	}


	private void initOutKeyAttr(StringBuilder s, String columnName, String columnType, String columnComment) {
		s.append("<tr><td><font color=\"red\">" + columnName + "</font></td>");
		s.append("<td><font color=\"red\">" + columnType + "</font></td>");
		s.append("<td><font color=\"red\">" + columnComment + "</font></td></tr>");
	}


}
