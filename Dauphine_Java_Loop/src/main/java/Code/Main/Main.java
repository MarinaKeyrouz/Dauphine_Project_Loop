package Code.Main;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import Code.GUI.GUI;
import Code.GUI.Grid;
import Code.Solve.Checker;
import Code.Solve.Generator;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Parser of the program
 *
 */
public class Main {
	private static String inputFile = null;
	private static String outputFile = null;
	private static Integer width = -1;
	private static Integer height = -1;
	private static Integer maxcc = -1;
	private static Integer nbThread = 1;
	private static Integer algo = 2;
	
	public static void main(String[] args) {
		Options options = new Options();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        
        options.addOption("g", "generate ", true, "Generate a grid of size height x width.");
        options.addOption("c", "check", true, "Check whether the grid in <arg> is solved.");
        
        options.addOption("s", "solve", true, "Solve the grid stored in <arg>.");   
        options.addOption("o", "output", true, "Store the generated or solved grid in <arg>. (Use only with --generate and --solve.)");
        options.addOption("t", "threads", true, "Maximum number of solver threads. (Use only with --solve.)");
        options.addOption("x", "nbcc", true, "Maximum number of connected components. (Use only with --generate.)");
        options.addOption("h", "help", false, "Display this help");
        
        try {
            cmd = parser.parse( options, args);         
        } catch (ParseException e) {
            System.err.println("Error: invalid command line format.");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "phineloopgen", options );
            System.exit(1);
        }
                
    try{    
        if( cmd.hasOption( "g" ) ) {
            System.out.println("Running solvable generator");
            String[] gridformat = cmd.getOptionValue( "g" ).split("x");
            width = Integer.parseInt(gridformat[0]);
            height = Integer.parseInt(gridformat[1]); 
            if(! cmd.hasOption("o")) throw new ParseException("Missing mandatory --output argument.");
            outputFile = cmd.getOptionValue( "o" );

            // generate grid and store it to outputFile...
            //...

            Grid g = new Grid(width, height);
            Grid grid = Generator.generator(outputFile,g );
            System.out.println(grid);
            GUI gui = new GUI(grid);

            Grid grid2 = Checker.buildGrid(outputFile);
            System.out.println(grid2);
            System.out.println(Checker.isSolved(grid2));

//            Grid grid3 = Checker.buildGrid("C:\\Users\\tinta\\Documents\\COURS M1\\Java avancé\\Infinity v2\\Dauphine_Project_Loop\\Files\\LevelTestAlmostSolved.txt");
//            System.out.println(grid3);
//            System.out.println(Checker.isWellConnectedAfterMove(1, 1,grid3, 1));
            //GUI gui = new GUI(grid);


            /*
            System.out.println(grid);
            Generator.writeGrid("../Levels/Level2.txt", grid);
            /*
            Solver sol = new Solver();
            sol.solveGrid(grille.getPiece(0,0),Solver.optimization(grille));
            System.out.println(sol.getSolvedGrid());
            */
        }
        else if( cmd.hasOption( "s" ) ) {
            System.out.println("Running phineloop solver.");
            inputFile = cmd.getOptionValue( "s" );
            if(! cmd.hasOption("o")) throw new ParseException("Missing mandatory --output argument.");      
            outputFile = cmd.getOptionValue( "o" );
            boolean solved = false; 
        
            // load grid from inputFile, solve it and store result to outputFile...
            // ...
            
            System.out.println("SOLVED: " + solved);            
        }
        
        else if( cmd.hasOption( "c" )) {
            System.out.println("Running phineloop checker.");
            inputFile = cmd.getOptionValue( "c" );
            boolean solved = false; 
            
            // load grid from inputFile and check if it is solved... 
            //...
            System.out.println("SOLVED: " + solved);           
        }
        else {
            throw new ParseException("You must specify at least one of the following options: -generate -check -solve ");           
        }
        } catch (ParseException e) {
            System.err.println("Error parsing commandline : " + e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "phineloopgen", options );         
            System.exit(1); // exit with error      
    }/* catch (MalformedURLException e) {
        e.printStackTrace();
    }*/ catch (MalformedURLException | FileNotFoundException e) {
        e.printStackTrace();
    }
        //System.exit(0); // exit with success
    }
	
}
