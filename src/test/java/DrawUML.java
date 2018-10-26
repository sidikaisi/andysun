import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 *  @Description
 *  @Author zhangxiaojun
 *  @Date 2018/10/24
 *  @Version 1.0.0
 **/
public class DrawUML {

	public  DrawUML(List<String> nodes,List<TableUml> umls){
		GraphViz gViz=new GraphViz("/Users/zhangxiaojun/Downloads", "/usr/local/bin/dot");
		gViz.start_graph();
		gViz.addln("node [shape = \"record\", fontname = \"Consolas\"];");
		gViz.addln("edge [arrowhead = \"empty\", fontname = \"Consolas\", color=red,weight=5,fontcolor=red,style=bold]");
		gViz.addln("rankdir=TB");
		nodes.forEach(s->{
			gViz.addln(s);
		});
		umls.forEach(s->{
			StringBuilder str = new StringBuilder();
			str.append(s.getStartTabLe()).append("->").append(s.getEndTabLe()).append("[label=\"");
			if(StringUtils.isEmpty(s.getLineComment())){
				str.append(s.getStartTabLeAttr()).append("=>").append(s.getEndTabLeAttr());
			}else{
				str.append(s.getLineComment());
			}
			str.append("\"];");
			gViz.addln(str.toString());
		});
		gViz.end_graph();
		try {
			gViz.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class GraphViz{
		private String runPath = "";
		private String dotPath = "";
		private String runOrder="";
		private String dotCodeFile="umlDot.txt";
		private String resultGif="umlDot";
		private StringBuilder graph = new StringBuilder();

		Runtime runtime=Runtime.getRuntime();

		public void run() {
			File file=new File(runPath);
			file.mkdirs();
			writeGraphToFile(graph.toString(), runPath);
			creatOrder();
			try {
				runtime.exec(runOrder);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void creatOrder(){
			runOrder+=dotPath+" ";
			runOrder+=runPath;
			runOrder+="/"+dotCodeFile+" ";
			runOrder+="-T gif ";
			runOrder+="-o ";
			runOrder+=runPath;
			runOrder+="/"+resultGif+".gif";
			System.out.println(runOrder);
		}

		public void writeGraphToFile(String dotcode, String filename) {
			try {
				File file = new File(filename+"/"+dotCodeFile);
				if(!file.exists()){
					file.createNewFile();
				}
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(dotcode.getBytes());
				fos.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}

		public GraphViz(String runPath,String dotPath) {
			this.runPath=runPath;
			this.dotPath=dotPath;
		}

		public void add(String line) {
			graph.append("\t"+line);
		}

		public void addln(String line) {
			graph.append("\t"+line + "\n");
		}

		public void addln() {
			graph.append('\n');
		}

		public void start_graph() {
			graph.append("digraph umlmodel {\n") ;
		}

		public void end_graph() {
			graph.append("}") ;
		}
	}
}
