package simulator.launcher;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import simulator.control.Controller;
import simulator.exceptions.UnsupportedFileException;
import simulator.factories.Builder;
import simulator.factories.BuilderBasedFactory;
import simulator.factories.Factory;
import simulator.factories.MostCrowdedStrategyBuilder;
import simulator.factories.MoveAllStrategyBuilder;
import simulator.factories.MoveFirstStrategyBuilder;
import simulator.factories.NewCityRoadEventBuilder;
import simulator.factories.NewInterCityRoadEventBuilder;
import simulator.factories.NewJunctionEventBuilder;
import simulator.factories.NewVehicleEventBuilder;
import simulator.factories.RoundRobinStrategyBuilder;
import simulator.factories.SetContClassEventBuilder;
import simulator.factories.SetWeatherEventBuilder;
import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.TrafficSimulator;
import simulator.view.MainWindow;

/**
 * @author Fernando Méndez Torrubiano
 *
 */

public class Main {

	private final static Integer _timeLimitDefaultValue = 10;
	private static int _timeLimitValue = 0;
	private static String _inFile = null;
	private static String _outFile = null;
	private static String _mode = "gui";
	private static boolean _GUI = true;
	private static Factory<Event> _eventsFactory = null;

	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);
			parseOutFileOption(line);
			parseTicksOption(line);
			parseModeOption(line);

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Events input file").build());
		cmdLineOptions.addOption(
				Option.builder("o").longOpt("output").hasArg().desc("Output file, where reports are written.").build());
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message").build());
		cmdLineOptions.addOption(Option.builder("t").longOpt("ticks").hasArg().desc("Select number of ticks.").build());
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Use GUI or Console.").build());

		return cmdLineOptions;
	}
	

	private static void parseModeOption(CommandLine line) throws ParseException {
		_mode = line.getOptionValue("m");
		if(_mode == null) {
			throw new ParseException("Mode not found");
		}
		else{
			if(_mode.toLowerCase().equals("console")) {
				_GUI = false;
			}
			else {
				_GUI = true;
			}
		}
	}
	
	private static void parseTicksOption(CommandLine line) {
		if(line.hasOption("t")) {
			//_timeLimitValue = Integer.parseInt(line.getArgList().get(0));
			_timeLimitValue = Integer.parseInt(line.getOptionValue("t"));
		}
	}
	
	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		_inFile = line.getOptionValue("i");
		if (_inFile == null) {
			throw new ParseException("An events file is missing");
		}
	}

	private static void parseOutFileOption(CommandLine line) throws ParseException {
		_outFile = line.getOptionValue("o");
	}

	private static void initFactories() {
		// TODO complete this method to initialize _eventsFactory
		
		List<Builder<LightSwitchingStrategy>> lsbs = new ArrayList<>();
		lsbs.add( new RoundRobinStrategyBuilder() );
		lsbs.add( new MostCrowdedStrategyBuilder() );
		Factory<LightSwitchingStrategy> lssFactory = new BuilderBasedFactory<>(lsbs);

		List<Builder<DequeuingStrategy>> dqbs = new ArrayList<>();
		dqbs.add( new MoveFirstStrategyBuilder() );
		dqbs.add( new MoveAllStrategyBuilder() );
		Factory<DequeuingStrategy> dqsFactory = new BuilderBasedFactory<>(dqbs);
		
		List<Builder<Event>> ebs = new ArrayList<>();
		ebs.add( new NewJunctionEventBuilder(lssFactory, dqsFactory) );
		ebs.add( new NewCityRoadEventBuilder() );
		ebs.add( new NewInterCityRoadEventBuilder() );
		ebs.add( new NewVehicleEventBuilder() );
		ebs.add( new SetWeatherEventBuilder() );
		ebs.add( new SetContClassEventBuilder() );
		_eventsFactory = new BuilderBasedFactory<>(ebs);

	}

	private static void startBatchMode() throws IOException {
		// TODO complete this method to start the simulation
		
		TrafficSimulator sim = new TrafficSimulator(); 
		Controller c = new Controller(sim, _eventsFactory);
		
		InputStream input = new FileInputStream(_inFile);
		OutputStream output = _outFile == null ? System.out : new FileOutputStream(_outFile);
		
		try {
			c.loadEvents(input);
		} catch (UnsupportedFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(_timeLimitValue == 0) {
			c.run(_timeLimitDefaultValue, output);
		}
		else {
			c.run(_timeLimitValue, output);
		}
		input.close();
		System.out.println("Fin de la simulaci�n.");
	}
	
	private static void startGUIMode() throws IOException {
		TrafficSimulator sim = new TrafficSimulator(); 
		Controller ctrl = new Controller(sim, _eventsFactory);
		
		InputStream input = _inFile == null ? System.in : new FileInputStream(_inFile);
		
		try {
			ctrl.loadEvents(input);
		} catch (UnsupportedFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		input.close();
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainWindow(ctrl);
			}
		});
		
		System.out.println("Iniciado el simulador.");
	}

	private static void start(String[] args) throws IOException {
		initFactories();
		parseArgs(args);
		if(_GUI == false) {
			startBatchMode();
		}
		else {
			startGUIMode();
		}
	}

	// example command lines:
	//
	// -i resources/examples/ex1.json
	// -i resources/examples/ex1.json -t 300
	// -i resources/examples/ex1.json -o resources/tmp/ex1.out.json
	// -i resources/examples/ex1.json -m gui
	// -i resources/examples/ex1.json -t 100 -m console
	// --help

	public static void main(String[] args) {
		try {
			start(args);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
