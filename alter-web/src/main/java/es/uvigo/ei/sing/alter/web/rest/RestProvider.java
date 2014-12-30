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

package es.uvigo.ei.sing.alter.web.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import es.uvigo.ei.sing.alter.converter.Converter;
import es.uvigo.ei.sing.alter.converter.DefaultFactory;
import es.uvigo.ei.sing.alter.converter.ProgramOptions;
import es.uvigo.ei.sing.alter.parser.ParseException;

class RestLogHandler extends Handler
{
  
    StringBuffer info;
    StringBuffer error;
    StringBuffer warn;
    public RestLogHandler(StringBuffer info, StringBuffer error, StringBuffer warn)
    {
      this.info = info;
      this.error = error;
      this.warn = warn;
    }
    
    @Override
    public void publish(LogRecord record)
    {
        try
        {
            if (record.getLevel() == Level.INFO)
            {
                info.append(record.getMessage().replaceAll("\n", "\n\t") + "\n");
                
            }
            else if (record.getLevel() == Level.WARNING)
            {
                
                warn.append(record.getMessage().replaceAll("\n", "\n\t") + "\n");
            }
            else
            {
                error.append(record.getMessage().replaceAll("\n", "\n\t") + "\n");
            }
        }
        catch(NullPointerException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void flush()
    {
    }

    @Override
    public void close() throws SecurityException
    {
    }
}


@Produces("text/plain")
@Path("/")
public class RestProvider {
	@POST
	@Produces("text/plain")
	@Path("/convert") 
	public String getPathways(
	  @QueryParam("inO") String inO_QP, 
	  @QueryParam("inP") String inP_QP,
	  @QueryParam("inF") String inF_QP,
	  @QueryParam("autodetect") boolean autodetect_QP,
	  @QueryParam("collapse") boolean collapse_QP,
	  @QueryParam("gapsAsMissing") boolean gapsAsMissing_QP,
	  @QueryParam("countMissing") boolean countMissing_QP,
	  @QueryParam("limit") int limit_QP,
	  @QueryParam("outO") String outO_QP,
	  @QueryParam("outP") String outP_QP,
	  @QueryParam("outF") String outF_QP,
	  @QueryParam("lowercase") boolean lowercase_QP,
	  @QueryParam("resNumbers") boolean resNumbers_QP,
	  @QueryParam("sequential") boolean sequential_QP,
	  @QueryParam("match") boolean match_QP,
	  @FormParam("inO") String inO_FP, 
	  @FormParam("inP") String inP_FP,
	  @FormParam("inF") String inF_FP,
	  @FormParam("autodetect") boolean autodetect_FP,
	  @FormParam("collapse") boolean collapse_FP,
	  @FormParam("gapsAsMissing") boolean gapsAsMissing_FP,
	  @FormParam("countMissing") boolean countMissing_FP,
	  @FormParam("limit") int limit_FP,
	  @FormParam("outO") String outO_FP,
	  @FormParam("outP") String outP_FP,
	  @FormParam("outF") String outF_FP,
	  @FormParam("lowercase") boolean lowercase_FP,
	  @FormParam("resNumbers") boolean resNumbers_FP,
	  @FormParam("sequential") boolean sequential_FP,
	  @FormParam("match") boolean match_FP,
	  @FormParam("sequence") String sequence){
	  
	  StringBuffer result = new StringBuffer();
	  try{
	    // PARAMS
	    String inO = inO_QP;
	    if (inO == null && inO_FP !=null){
	      inO = inO_FP;
	    }
	    
	    String inP = inP_QP;
	    if (inP == null && inP_FP !=null){
	      inP = inP_FP;
	    }
	    
	    String inF = inF_QP;
	    if (inF == null && inF_FP !=null){
	      inF = inF_FP;
	    }
	    
	    boolean autodetect = false;
	    if (autodetect_QP || autodetect_FP){
	      autodetect = true;
	    }
	    
	    boolean collapse = collapse_QP;
	    if (collapse_QP || collapse_FP){
	      collapse = true;
	    }
	    
	    boolean gapsAsMissing = gapsAsMissing_QP;
	    if (gapsAsMissing_QP || gapsAsMissing_FP){
	      gapsAsMissing = true;
	    }
	    
	    boolean countMissing = countMissing_QP;
	    if (countMissing_QP || countMissing_FP){
	      countMissing = true;
	    }
	    
	    
	    int limit = limit_QP;
	    if (limit_QP==0 && limit_FP!=0){
	      limit = limit_FP;
	    }
	    
	    String outO = outO_QP;
	    if (outO == null && outO_FP !=null){
	      outO = outO_FP;
	    }
	    
	    String outP = outP_QP;
	    if (outP == null && outP_FP !=null){
	      outP = outP_FP;
	    }
	    
	    String outF = outF_QP;
	    if (outF == null && outF_FP !=null){
	      outF = outF_FP;
	    }
	    
	    boolean lowercase = lowercase_QP;
	    if (lowercase_QP || lowercase_FP){
	      lowercase = true;
	    }
	    
	    boolean resNumbers = resNumbers_QP;
	    if (resNumbers_QP || resNumbers_FP){
	      resNumbers = true;
	    }
	    
	    boolean sequential = sequential_QP;
	    if (sequential_QP || sequential_FP){
	      sequential = true;
	    }
	    
	    boolean match = match_QP;
	    if (match_QP || match_FP){
	      match = true;
	    }

	    String currentLogger = ""+System.currentTimeMillis();
	    Logger logger = Logger.getLogger(currentLogger);
	    
	    StringBuffer info = new StringBuffer();
	    StringBuffer error = new StringBuffer();
	    StringBuffer warn = new StringBuffer();
	    logger.setLevel(Level.ALL);
	    logger.addHandler(new RestLogHandler(info, error, warn));
	    
	    Converter conv = new DefaultFactory().getConverter(inO, inP, inF, autodetect, collapse, gapsAsMissing, countMissing, limit, outO, outP, outF, lowercase, resNumbers, sequential,match, currentLogger);
	    
	    
	    result.append("------ CONVERSION RESULT -----\n");
	    result.append("\n------ Input Parameters -----\n");
	    result.append("autodetect: "+autodetect+"\n");
	    result.append("inO: "+inO+"\n");
	    result.append("inP: "+inP+"\n");
	    result.append("inF: "+inF+"\n");
	    result.append("outO: "+outO+"\n");
	    result.append("outP: "+outP+"\n");
	    result.append("outF: "+outF+"\n");
	    result.append("sequential: "+sequential+"\n");
	    result.append("match: "+match+"\n");
	    result.append("lowercase: "+lowercase+"\n");
	    result.append("resNumbers: "+resNumbers+"\n");
	    result.append("collapse: "+collapse+"\n");
	    result.append("gapsAsMissing: "+gapsAsMissing+"\n");
	    result.append("countMissing: "+countMissing+"\n");
	    result.append("limit: "+limit+"\n");
	    
	    
	    
	    
	    try{	      
	      String converted = conv.convert(sequence);
	      result.append("\n------ Info Log -----\n");
	      result.append(info.toString());
	      result.append("\n------ Warning Log -----\n");
	      result.append(warn.toString());
	      result.append("\n------ Error Log -----\n");
	      result.append(error.toString());
	      result.append("\n----- Converted Sequence ----\n");
	      
	      result.append(converted);      
	    }catch(ParseException e){
	      result.append("SEQUENCE PARSE EXCEPTION: "+e.getMessage());
	    }
	  }catch(Exception e){
	    result.append("EXCEPTION PROCESSING REQUEST: "+e.getMessage());
	  }
	  return result.toString();
	}
	
	@GET
	@Produces("text/plain")
	@Path("/so")
	public String getSO(){
		String toret="";
		for (String so : ProgramOptions.getSO()){
		  toret+=so+"\n";
		}
		return toret;
	}
	
	@GET
	@Produces("text/plain")
	@Path("/input/programs")
	public String getInputPrograms(){
		String toret="";
		for (String program : ProgramOptions.getInputPrograms()){
		  toret+=program+"\n";
		}
		return toret;
	}
	
	@GET
	@Produces("text/plain")
	@Path("/input/formats")
	public String getInputFormats(){
		String toret="";
		for (String format: ProgramOptions.getInputFormats()){
		  toret+=format+"\n";
		}
		return toret;
	}
	@GET
	@Produces("text/plain")
	@Path("/output/programs")
	public String getOutputPrograms(){
		String toret="";
		for (String program: ProgramOptions.getOutputPrograms()){
		  toret+=program+"\n";
		}
		return toret;
	}
	
	@GET
	@Produces("text/plain")
	@Path("/output/formats")
	public String getOutputFormats(){
		String toret="";
		for (String format: ProgramOptions.getOutputFormats()){
		  toret+=format+"\n";
		}
		return toret;
	}
	
	@GET
	@Produces("text/plain")
	@Path("/output/{program}/formats")
	public String getOutputProgramFormats(@PathParam("program") String program){
		String toret="";
		for (String format: ProgramOptions.getOutputProgramFormats(program)){
		  toret+=format+"\n";
		}
		return toret;
	}
	
	@GET
	@Produces("text/plain")
	@Path("/output/{program}/{format}/options")
	public String getOutputProgramOptions(@PathParam("program") String program, @PathParam("format") String format){
		String toret="";
		for (String option: ProgramOptions.getOutputProgramOptions(program, format)){
		  toret+=option+"\n";
		}
		return toret;
	}
	
	
	@GET
	@Produces("text/html")
	@Path("/form")
	public String showForm() throws IOException{
		String HOST = "http://sing.ei.uvigo.es/ALTER";
		BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("form.html")));
		String line = null;
		StringBuffer out = new StringBuffer();
		while ((line = reader.readLine())!=null){
			out.append(line+"\n");
		}
		return out.toString().replaceAll("%%HOST%%", HOST);
	}
	
	@GET
	@Produces("text/html")
	@Path("/")
	public String showHelp() throws IOException{
		String HOST = "http://sing.ei.uvigo.es/ALTER";
		BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("REST.html")));
		String line = null;
		StringBuffer out = new StringBuffer();
		while ((line = reader.readLine())!=null){
			out.append(line+"\n");
		}
		return out.toString().replaceAll("%%HOST%%", HOST);
		
		
	}
}
