package com.andersonlfeitosa.mavendependencyanalyzer;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.andersonlfeitosa.mavendependencyanalyzer.log.Log;
import com.andersonlfeitosa.mavendependencyanalyzer.manager.MavenDependencyAnalyzer;


public class Main {
	
	public static void main(String[] args) {
		new Main().execute(args);
	}

	public void execute(String[] args) {
		CommandLineParser parser = new BasicParser();
		Options options = configureCommandLineArgs();
		
		try {
			CommandLine line = parser.parse(options, args);
			String directory = line.getOptionValue("d");
			boolean debug = line.hasOption("v");
			
			if (debug) {
				Log.getLogger().setDebug();
			}
			
			MavenDependencyAnalyzer.getInstance().execute(directory);
		} catch (ParseException e) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(" ", options);
			System.out.println(e.getMessage());
		}
	}

	@SuppressWarnings("static-access")
	private Options configureCommandLineArgs() {
		Options options = new Options();

		Option directory = OptionBuilder.withArgName("root directory or root pom.xml").isRequired()
				.hasArgs().withDescription("Root directory or root pom.xml to be analyzed")
				.create("d");

		Option debug = OptionBuilder.withArgName("debug")
				.withDescription("Verbose mode").create("v");

		options.addOption(directory);
		options.addOption(debug);

		return options;
	}

}
