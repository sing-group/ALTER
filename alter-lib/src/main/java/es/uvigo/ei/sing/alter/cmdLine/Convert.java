/*
 *  This file is part of ALTER.
 *
 *  ALTER is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  ALTER is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with ALTER.  If not, see <http://www.gnu.org/licenses/>.
 */

package es.uvigo.ei.sing.alter.cmdLine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import es.uvigo.ei.sing.alter.converter.Converter;
import es.uvigo.ei.sing.alter.converter.DefaultFactory;
import es.uvigo.ei.sing.alter.converter.Factory;
import es.uvigo.ei.sing.alter.parser.ParseException;

/**
 * Main class to execute ALTER from the command line.
 * @author Daniel Gomez Blanco
 * @version 1.0
 */
public class Convert
{
    /**
     * Main method to execute ALTER from the command line.
     * @param args Conversion options.
     */
    public static void main(String args[])
    {
        //If there are no parameters launch the GUI version of ALTER
        if (args.length == 0)
            es.uvigo.ei.sing.alter.gui.MainFrame.main(args);

        //In other case use the command line
        else
        {
            //Initialize logger
            Logger logger = Logger.getLogger("alter" + System.currentTimeMillis());
            logger.setUseParentHandlers(false);
            logger.setLevel(Level.ALL);
            logger.addHandler(new CmdLineLogHandler());

            //Parse options
            CmdLineOptions opts = new CmdLineOptions();
            CmdLineParser parser = new CmdLineParser(opts);
            try
            {
                parser.parseArgument(args);
            }
            catch (CmdLineException e)
            {
                System.err.println(e.getMessage());
                parser.printUsage(System.err);
                return;
            }
            if (!opts.autodetect && (opts.inOS == null || opts.inProgram == null || opts.inFormat == null))
            {
                System.err.println("Autodetection not enabled. Input options required.");
                parser.printUsage(System.err);
                return;
            }

            //Load input file
            StringBuffer in = new StringBuffer();
            try
            {
                BufferedReader br = new BufferedReader(new FileReader(opts.in));
                String s;
                while ((s = br.readLine()) != null)
                    in.append(s + "\r\n");
                br.close();
            }

            catch (FileNotFoundException ex)
            {
                logger.log(Level.SEVERE, "Input file " + opts.in + " not found.");
                return;
            }
            catch (IOException ex)
            {
                logger.log(Level.SEVERE, "Failure reading input file " + opts.in + ":\r\n" + ex.getMessage());
                return;
            }
            //Get converter and convert
            Factory factory = new DefaultFactory();
            Converter converter;
            String out = "";

            try
            {
                converter = factory.getConverter(opts.inOS, opts.inProgram, opts.inFormat, opts.autodetect,
                        opts.collapse, opts.gaps, opts.missing, opts.limit,
                        opts.outOS, opts.outProgram, opts.outFormat, opts.lowerCase,
                        opts.residueNumbers, opts.sequential, opts.match, logger.getName());
                out = converter.convert(in.toString());
            }
            catch (UnsupportedOperationException ex)
            {
                logger.log(Level.SEVERE, ex.getMessage());
                return;
            }
            catch (ParseException ex)
            {
                logger.log(Level.SEVERE, "Failure parsing source file:\r\n" + ex.getMessage());
                return;
            }

            //Output to file
            try
            {
                FileWriter fw = new FileWriter(opts.out);
                fw.write(out);
                fw.close();
            }
            catch (IOException ex)
            {
                logger.log(Level.SEVERE, "Failure writing output file:\r\n" + ex.getMessage());
                System.err.println(ex.getMessage());
            }

        }
    }
}
