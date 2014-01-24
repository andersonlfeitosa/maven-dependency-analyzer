package com.andersonlfeitosa.mavendependencyanalyzer.graphviz;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.andersonlfeitosa.mavendependencyanalyzer.log.Log;

public class GraphvizEngine {

	private static final Log log = Log.getInstance();

	private Map<String, OutputType> type;
	private Graph graph;
	private String layoutManager;

	private String directoryPathExecute = ".";

	public GraphvizEngine(Graph graph) {
		this.graph = graph;
		this.type = new HashMap<String, OutputType>();
		this.type.put("png", new OutputType("png"));
		this.layoutManager = "dot";
	}

	public void output() {
		String dotContent = graph.output();

		try {
			String prog = findExecutable(layoutManager);
			File tmpDot = createDotFileTemp("in", dotContent);

			StringBuilder outputTypes = new StringBuilder();
			for (OutputType type : this.type.values()) {
				outputTypes.append(" -T").append(type.name()).append(" -o")
						.append(type.filePath());
			}

			String dotCommand = prog + outputTypes + " " + tmpDot.getPath();
			Process process = Runtime.getRuntime().exec(dotCommand, null,
					new File(directoryPathExecute));

			@SuppressWarnings("unused")
			int exitVal = process.waitFor();

		} catch (IOException e) {
			log.error(e);
			throw new GraphvizOutputException(e.getMessage(), e);
		} catch (InterruptedException e) {
			log.error(e);
			throw new GraphvizOutputException(e.getMessage(), e);
		}

	}

	private String findExecutable(String prog) {
		String[] paths = System.getenv().get("PATH").split(File.pathSeparator);
		for (String path : paths) {
			String file = (path == null) ? prog
					: (path + File.separator + prog);
			if (new File(file).canExecute() && !new File(file).isDirectory()) {
				return file;
			}
		}
		throw new GraphvizEngineException(prog + " program not found.");
	}

	private File createDotFileTemp(String suffix, String dotContent) {
		try {
			File temp = File.createTempFile("graph", suffix);
			if (dotContent != null) {
				BufferedWriter out = new BufferedWriter(new FileWriter(temp));
				out.write(dotContent);
				out.close();
			}
			return temp;
		} catch (IOException e) {
			throw new GraphvizOutputException(e.getMessage(), e);
		}
	}

	public List<OutputType> types() {
		return new ArrayList<OutputType>(type.values());
	}

	public GraphvizEngine fromDirectoryPath(String path) {
		this.directoryPathExecute = path;
		return this;
	}

	public OutputType addType(String name) {
		OutputType output = type.get(name);
		if (output == null) {
			output = new OutputType(name);
			type.put(name, output);
		}

		return this.type.get(name);
	}

	public GraphvizEngine removeType(String name) {
		if (type.size() == 1) {
			throw new IllegalStateException("must be a type defined.");
		}

		type.remove(name);

		return this;
	}

	public GraphvizEngine layout(String layoutManager) {
		this.layoutManager = layoutManager;
		return this;
	}

	public GraphvizEngine toFilePath(String filePath) {
		if (this.type.size() > 1) {
			throw new IllegalStateException("there was more of a type defined.");
		}

		this.type.values().iterator().next().toFilePath(filePath);

		return this;
	}

}
