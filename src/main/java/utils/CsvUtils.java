package utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CsvUtils {

	public final static String SPLIT_LINE = "-";

	public final static String SPLIT_COMMA = ",";

	public final static String NEED_CONVERT = "1";

	public final static String SPLIT_DOT = ".";

	public final static String CHARSET_NAME = "gb2312";

	public final static String CONTENT_TYPE_CSV = "text/csv";

	public final static String NEW_LINE = "\r\n";

	/**
	 * @Author zhangxiaojun
	 * @Description 导出数据的处理工具类
	 * @Date 上午10:32 2018/9/13
	 * @Param [datas, headers, converts, response, fileName]
	 * @return void
	 * @Version 1.0.0
	 **/
	public static void exportCSV2Response(List<?> datas, List<String> headers, Map<String, Object> converts,
			HttpServletResponse response, String fileName) {
		BufferedWriter buffCvsWriter = null;
		try {
			fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
			response.setContentType(CONTENT_TYPE_CSV);
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"; filename=utf-8''");
			buffCvsWriter = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), CHARSET_NAME));
			StringBuilder stringBuilder = new StringBuilder();
			for (int a=0;a<headers.size();a++){
				String head = headers.get(a).split(SPLIT_COMMA)[0];
				stringBuilder.append("\"" + head + "\"");
				if(a < headers.size() -1){
					stringBuilder.append(SPLIT_COMMA);
				}
			}
			stringBuilder.append(NEW_LINE);
			for (int i = 0; i < datas.size(); i++) {
				for (int j = 0; j < headers.size(); j++) {
					String s = headers.get(j).split(SPLIT_COMMA)[1];
					String[] split = s.split(SPLIT_LINE);
					String property = split[0];
					Object val = getVal(datas.get(i), property);
					if (split.length > 1 && NEED_CONVERT.equals(split[1])) {
						val = converts.get(property + SPLIT_LINE + val.toString());
					}
					if (val == null) {
						val = "";
					}
					if (val instanceof Date) {
						val = DateUtils.formatDate2Str((Date) val, DateUtils.YYYY_MM_DD_HH_MM_SS);
					}
					stringBuilder.append("\"" + val.toString() + "\"");
					if (j < headers.size() - 1) {
						stringBuilder.append(SPLIT_COMMA);
					}
				}
				stringBuilder.append(NEW_LINE);
			}
			buffCvsWriter.write(stringBuilder.toString());
			buffCvsWriter.flush();
		} catch (IOException e) {
			log.error("<<<<<<e={}", e.getMessage());
		} finally {
			// 释放资源
			if (buffCvsWriter != null) {
				try {
					buffCvsWriter.close();
				} catch (IOException e) {
					log.error("<<<<<<e={}", e.getMessage());
				}
			}
		}
	}

	public static Object getVal(Object curr, String methodPath) {
		String[] split = methodPath.split("\\" + SPLIT_DOT);
		Object obj = curr;
		try {
			for (String s : split) {
				Class<?> clazz = obj.getClass();
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
				Method method1 = clazz.getMethod(methodName, new Class[]{});
				Object objVal = method1.invoke(obj, new Object[]{});
				obj = objVal;
			}
		} catch (Exception e) {
			log.error("<<<<<<e={}", e.getMessage());
		}
		return obj;
	}


}
