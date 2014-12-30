package es.uvigo.ei.sing.alter.web;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Html;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.North;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import es.uvigo.ei.sing.alter.converter.DefaultFactory;
import es.uvigo.ei.sing.alter.converter.Factory;
import es.uvigo.ei.sing.alter.parser.ParseException;
import es.uvigo.ei.sing.alter.reader.Reader;
import es.uvigo.ei.sing.alter.types.MSA;
import es.uvigo.ei.sing.alter.writer.Writer;

public class Controller extends Window
{
    Logger logger;
    MSA msa;
    String ext = "";
    boolean collapsed;
    String filename = "";
    StringBuffer inputText = new StringBuffer(20000);
    StringBuffer outputText = new StringBuffer(20000);
    final int maxChars = 20000;

    public Controller()
    {
        super();
    }

    public Controller(String title, String border, boolean closable)
    {
        super(title, border, closable);
    }

    public void setInputText(String s)
    {
        inputText= new StringBuffer(s);
    }

    public void load(UploadEvent event) throws InterruptedException
    {
        Textbox input = (Textbox) getFellow("input");
        Media media = event.getMedia();
        String text = "";
        
        if (media != null)
        {
            filename = media.getName();
            if (media.isBinary())
                if (media.inMemory())
                {
                    StringBuffer buffer = new StringBuffer();
                    byte[] bytes = media.getByteData();
                    for (int i = 0; i < bytes.length; i++)
                        buffer.append((char) bytes[i]);
                    text = buffer.toString();
                }
                else
                {
                    StringWriter writer = new StringWriter();
                    try
                    {
                        IOUtils.copy(media.getStreamData(), writer);
                    }
                    catch (IOException ex)
                    {
                        logger.log(Level.SEVERE, ex.getMessage());
                        return;
                    }
                    text = writer.toString();
                }
            else
                if (media.inMemory())
                    text = media.getStringData();
                else
                {
                    StringWriter writer = new StringWriter();
                    try
                    {
                        IOUtils.copy(media.getReaderData(), writer);
                    }
                    catch (IOException ex)
                    {
                        logger.log(Level.SEVERE, ex.getMessage());
                        return;
                    }
                    text = writer.toString();

                }
        }

        inputText = new StringBuffer(text);

        if (text.length() < maxChars)
            input.setRawValue(text);
        else
        {
            input.setRawValue(inputText.substring(0, maxChars));
            Messagebox.show("The complete input text is not being displayed due to its length. Text edition will be disabled to avoid erros in data.",
                    "Input text too long", Messagebox.OK, Messagebox.INFORMATION);
        }
        input.setFocus(true);
        read();
    }

    public void save()
    {
        String out = outputText.toString();
        Listitem outputOSItem = ((Listbox) getFellow("outputOS")).getSelectedItem();
        if (outputOSItem == null)
            outputOSItem = ((Listbox) getFellow("outputOS")).getItemAtIndex(0);
        String outputOS = (String)outputOSItem.getValue();

        String name;
        if (filename.isEmpty())
            name = "convertedmsa";
        else
            name = filename + ".alter";

        if(collapsed)
            name += ".haps";

        if(ext.equals("ALN"))
            name += ".aln";
        else if (ext.equals("FASTA"))
            name += ".fas";
        else if (ext.equals("GDE"))
            name += ".gde";
        else if (ext.equals("MEGA"))
            name += ".meg";
        else if (ext.equals("MSF"))
            name += ".msf";
        else if (ext.equals("NEXUS"))
            name += ".nex";
        else if (ext.equals("PHYLIP"))
            name += ".phy";
        else if (ext.equals("PIR"))
            name += ".pir";

        if (outputOS.equals("Linux"))
            Filedownload.save(out, null, name);
        else if (outputOS.equals("MacOS"))
            Filedownload.save(out.replace("\n", "\r"), null, name);
        else
            Filedownload.save(out.replace("\n", "\r\n"), null, name);
    }

    public void onAutodetectCheck()
    {
        boolean autodetect = ((Checkbox) getFellow("autodetect")).isChecked();
        Listbox inputOS = (Listbox) getFellow("inputOS");
        Label inputOSLabel = (Label) getFellow("inputOSLabel");
        Html andLoad = (Html) getFellow("andLoad");
        Button load = (Button) getFellow("load");
        Listbox inputProgram = (Listbox) getFellow("inputProgram");
        Label inputProgramLabel = (Label) getFellow("inputProgramLabel");
        Listbox inputFormat = (Listbox) getFellow("inputFormat");
        Html andInputFormat = (Html) getFellow("andInputFormat");
        Label inputFormatLabel = (Label) getFellow("inputFormatLabel");
        Textbox input = (Textbox) getFellow("input");
        Center step2 = (Center) getFellow("step2");
        Html orPaste = (Html) getFellow("orPaste");
        Label pasteLabel = (Label) getFellow("pasteLabel");
        Button paste = (Button) getFellow("paste");

        if(autodetect)
        {
            inputProgram.setSelectedIndex(0);
            inputProgram.setDisabled(true);
            inputProgramLabel.setSclass("gray");

            inputFormat.setSelectedIndex(0);
            inputFormat.setDisabled(true);
            andInputFormat.setSclass("gray");
            inputFormatLabel.setSclass("gray");

            input.setDisabled(false);
            step2.setZclass("step2");
            orPaste.setSclass("black");
            pasteLabel.setSclass("black");
            paste.setDisabled(false);

            inputOS.setDisabled(false);
            inputOSLabel.setSclass("black");
            andLoad.setSclass("black");
            load.setDisabled(false);
        }
        else
        {
            inputProgram.setDisabled(false);
            inputProgramLabel.setSclass("black");

            inputOS.setSelectedIndex(0);
            inputOS.setDisabled(true);
            inputOSLabel.setSclass("gray");
            andLoad.setSclass("gray");
            load.setDisabled(true);
            
            input.setDisabled(true);
            step2.setZclass("step2bw");
            orPaste.setSclass("gray");
            pasteLabel.setSclass("gray");
            paste.setDisabled(true);
        }
    }

    public void onInputProgramSelect()
    {
        Listbox inputProgram = (Listbox) getFellow("inputProgram");
        Listbox inputFormat = ((Listbox) getFellow("inputFormat"));
        Html andInputFormat = (Html) getFellow("andInputFormat");
        Label inputFormatLabel = (Label) getFellow("inputFormatLabel");

        if (inputProgram.getSelectedItem() != null)
        {
            String program = ((Listbox) getFellow("inputProgram")).getSelectedItem().getLabel();
            if (!program.equals("<select>"))
            {
                if (program.equals("Clustal"))
                    inputFormat.setModel(new SimpleListModel(new String[]
                            {
                                "<select>","ALN", "FASTA", "GDE", "MSF", "NEXUS", "PHYLIP", "PIR"
                            }));
                else if (program.equals("MAFFT"))
                    inputFormat.setModel(new SimpleListModel(new String[]
                            {
                                "<select>","ALN","FASTA"
                            }));
                else if (program.equals("MUSCLE"))
                    inputFormat.setModel(new SimpleListModel(new String[]
                            {
                                "<select>","ALN","FASTA","MSF","PHYLIP"
                            }));
                else if (program.equals("PROBCONS"))
                    inputFormat.setModel(new SimpleListModel(new String[]
                            {
                                "<select>","ALN", "FASTA"
                            }));
                else if (program.equals("TCoffee"))
                    inputFormat.setModel(new SimpleListModel(new String[]
                            {
                                "<select>","ALN", "FASTA", "MSF", "PHYLIP", "PIR"
                            }));
                inputFormat.setDisabled(false);
                andInputFormat.setSclass("black");
                inputFormatLabel.setSclass("black");
                onInputFormatSelect();
            }
            else
            {   inputFormat.setSelectedIndex(0);
                inputFormat.setDisabled(true);
                andInputFormat.setSclass("gray");
                inputFormatLabel.setSclass("gray");
                onInputFormatSelect();
            }
        }
        else
        {
            inputFormat.setSelectedIndex(0);
            inputFormat.setDisabled(true);
            andInputFormat.setSclass("gray");
            inputFormatLabel.setSclass("gray");
            onInputFormatSelect();
        }
           
    }

    public void onInputFormatSelect()
    {
        Listbox inputFormat = (Listbox) getFellow("inputFormat");
        Listbox inputOS = (Listbox) getFellow("inputOS");
        Label inputOSLabel = (Label) getFellow("inputOSLabel");
        Html andLoad = (Html) getFellow("andLoad");
        Button load = (Button) getFellow("load");
        Textbox input = (Textbox) getFellow("input");
        Center step2 = (Center) getFellow("step2");
        Html orPaste = (Html) getFellow("orPaste");
        Label pasteLabel = (Label) getFellow("pasteLabel");
        Button paste = (Button) getFellow("paste");

        if (inputFormat.getSelectedItem() != null)
            if (!inputFormat.getSelectedItem().getLabel().equals("<select>"))
            {
                inputOS.setDisabled(false);
                inputOSLabel.setSclass("black");
                andLoad.setSclass("black");
                load.setDisabled(false);

                input.setDisabled(false);
                step2.setZclass("step2");
                orPaste.setSclass("black");
                pasteLabel.setSclass("black");
                paste.setDisabled(false);
            }
            else
            {
                inputOS.setSelectedIndex(0);
                inputOS.setDisabled(true);
                inputOSLabel.setSclass("gray");
                andLoad.setSclass("gray");
                load.setDisabled(true);

                input.setDisabled(true);
                step2.setZclass("step2bw");
                orPaste.setSclass("gray");
                pasteLabel.setSclass("gray");
                paste.setDisabled(true);
            }
        else
        {
            inputOS.setSelectedIndex(0);
            inputOS.setDisabled(true);
            inputOSLabel.setSclass("gray");
            andLoad.setSclass("gray");
            load.setDisabled(true);

            input.setDisabled(true);
            step2.setZclass("step2bw");
            orPaste.setSclass("gray");
            pasteLabel.setSclass("gray");
            paste.setDisabled(true);
        }
            
    }

    public void onCollapseCheck()
    {
        boolean collapse = ((Checkbox) getFellow("collapse")).isChecked();
        Spinner limit = (Spinner) getFellow("limit");
        Label limitLabel = (Label) getFellow("limitLabel");
        Checkbox gapsAsMissing = (Checkbox) getFellow("gapsAsMissing");
        Checkbox countMissing = (Checkbox) getFellow("countMissing");

        if(collapse)
        {
            limit.setDisabled(false);
            limitLabel.setSclass("black");
            gapsAsMissing.setDisabled(false);
            gapsAsMissing.setSclass("black");
            countMissing.setDisabled(false);
            countMissing.setSclass("black");
        }
        else
        {
            limit.setDisabled(true);
            limitLabel.setSclass("gray");
            gapsAsMissing.setDisabled(true);
            gapsAsMissing.setSclass("gray");
            countMissing.setDisabled(true);
            countMissing.setSclass("gray");
        }
    }
    public void onOutputProgramSelect()
    {
        Listbox outputFormat = (Listbox) getFellow("outputFormat");
        Html andOutputFormat = (Html) getFellow("andOutputFormat");
        Label outputFormatLabel = (Label) getFellow("outputFormatLabel");
        Listbox outputProgram = (Listbox) getFellow("outputProgram");

        if (outputProgram.getSelectedItem() != null)
        {
            String program = outputProgram.getSelectedItem().getLabel();
            if (!program.equals("<select>"))
            {
                program = program.substring(4);
                if (program.equals("General"))
                    outputFormat.setModel(new SimpleListModel(new String[]
                            {
                                "<select>","ALN", "FASTA", "GDE", "MEGA", "MSF", "NEXUS", "PHYLIP", "PIR"
                            }));
                else if (program.equals("jModelTest"))
                    outputFormat.setModel(new SimpleListModel(new String[]
                            {
                                "<select>","ALN","FASTA", "MSF", "NEXUS", "PHYLIP", "PIR"
                            }));
                else if (program.equals("MrBayes"))
                    outputFormat.setModel(new SimpleListModel(new String[]
                            {
                                "<select>","NEXUS"
                            }));
                else if (program.equals("PAML"))
                    outputFormat.setModel(new SimpleListModel(new String[]
                            {
                                "<select>","NEXUS", "PHYLIP"
                            }));
                else if (program.equals("PAUP"))
                    outputFormat.setModel(new SimpleListModel(new String[]
                            {
                                "<select>","MEGA", "MSF", "NEXUS", "PHYLIP", "PIR"
                            }));
                else if (program.equals("PhyML"))
                    outputFormat.setModel(new SimpleListModel(new String[]
                            {
                                "<select>","PHYLIP"
                            }));
                else if (program.equals("ProtTest"))
                    outputFormat.setModel(new SimpleListModel(new String[]
                            {
                                "<select>","NEXUS","PHYLIP"
                            }));
                else if (program.equals("RAxML"))
                    outputFormat.setModel(new SimpleListModel(new String[]
                            {
                                "<select>","PHYLIP"
                            }));
                else if (program.equals("RAxML"))
                    outputFormat.setModel(new SimpleListModel(new String[]
                            {
                                "<select>","NEXUS", "PHYLIP"
                            }));
                else if (program.equals("TCS"))
                    outputFormat.setModel(new SimpleListModel(new String[]
                            {
                                "<select>","NEXUS", "PHYLIP"
                            }));
                else if (program.equals("CodABC"))
                    outputFormat.setModel(new SimpleListModel(new String[]
                            {
                                "<select>", "PHYLIP"
                            }));
                else if (program.equals("BioEdit"))
                    outputFormat.setModel(new SimpleListModel(new String[]
                            {
                                "<select>","ALN","FASTA", "MSF", "NEXUS", "PHYLIP", "PIR"
                            }));
                else if (program.equals("MEGA"))
                    outputFormat.setModel(new SimpleListModel(new String[]
                            {
                                "<select>","ALN","FASTA","MEGA","MSF", "NEXUS", "PHYLIP", "PIR"
                            }));
                else if (program.equals("dnaSP"))
                    outputFormat.setModel(new SimpleListModel(new String[]
                            {
                                "<select>","FASTA","MEGA","NEXUS","PHYLIP","PIR"
                            }));
                else if (program.equals("Se-Al"))
                    outputFormat.setModel(new SimpleListModel(new String[]
                            {
                                "<select>","FASTA","GDE","NEXUS","PHYLIP","PIR"
                            }));
                else if (program.equals("Mesquite"))
                    outputFormat.setModel(new SimpleListModel(new String[]
                            {
                                "<select>","NEXUS"
                            }));
                else if (program.equals("SplitsTree"))
                    outputFormat.setModel(new SimpleListModel(new String[]
                            {
                                "<select>","ALN","FASTA","NEXUS","PHYLIP"
                            }));
                else if (program.equals("Clustal"))
                    outputFormat.setModel(new SimpleListModel(new String[]
                            {
                                "<select>","ALN","FASTA","GDE","MSF","PIR"
                            }));
                else if (program.equals("MAFFT"))
                    outputFormat.setModel(new SimpleListModel(new String[]
                            {
                                "<select>","FASTA"
                            }));
                else if (program.equals("MUSCLE"))
                    outputFormat.setModel(new SimpleListModel(new String[]
                            {
                                "<select>","FASTA"
                            }));
                else if (program.equals("PROBCONS"))
                    outputFormat.setModel(new SimpleListModel(new String[]
                            {
                                "<select>","FASTA"
                            }));
                else if (program.equals("TCoffee"))
                    outputFormat.setModel(new SimpleListModel(new String[]
                            {
                                "<select>","ALN","FASTA","MSF","PIR"
                            }));
                else if (program.equals("Gblocks"))
                    outputFormat.setModel(new SimpleListModel(new String[]
                            {
                                "<select>","FASTA","PIR"
                            }));
                else if (program.equals("SeaView"))
                    outputFormat.setModel(new SimpleListModel(new String[]
                            {
                                "<select>","ALN","FASTA","MSF","NEXUS","PHYLIP"
                            }));
                else if (program.equals("trimAl"))
                    outputFormat.setModel(new SimpleListModel(new String[]
                            {
                                "<select>","ALN","FASTA","MEGA","NEXUS","PHYLIP","PIR"
                            }));

                outputFormat.setDisabled(false);
                andOutputFormat.setSclass("black");
                outputFormatLabel.setSclass("black");
                onOutputFormatSelect();
            }
            else
            {
                outputFormat.setSelectedIndex(0);
                outputFormat.setDisabled(true);
                andOutputFormat.setSclass("gray");
                outputFormatLabel.setSclass("gray");
                onOutputFormatSelect();
            }
        }
        else
        {
            outputFormat.setSelectedIndex(0);
            outputFormat.setDisabled(true);
            andOutputFormat.setSclass("gray");
            outputFormatLabel.setSclass("gray");
            onOutputFormatSelect();
        }

    }
    public void onOutputFormatSelect()
    {
        String program = ((Listbox) getFellow("outputProgram")).getSelectedItem().getLabel().substring(4);
        
        Checkbox residueNumbers = (Checkbox) getFellow("numbers");
        Checkbox sequential = (Checkbox) getFellow("sequential");
        Checkbox match = (Checkbox) getFellow("match");
        Checkbox lowerCase = (Checkbox) getFellow("lower");
        Checkbox collapse = (Checkbox) getFellow("collapse");
        Button convert = (Button) getFellow("convert");
        Html andConvert = (Html) getFellow("andConvert");

        String format;
        if( ((Listbox) getFellow("outputFormat")).getSelectedItem() == null)
            format = "<select>";
        else
            format = ((Listbox) getFellow("outputFormat")).getSelectedItem().getLabel();

        if(!format.equals("<select>"))
        {
            //Lower case
            lowerCase.setDisabled(false);
            lowerCase.setSclass("black");

            //Collapse
            collapse.setDisabled(false);
            collapse.setSclass("black");
            onCollapseCheck();

            //Convert button
            convert.setDisabled(false);
            andConvert.setSclass("black");

            //Residue numbers checkbox
            if (format.equals("ALN"))
            {
                residueNumbers.setDisabled(false);
                residueNumbers.setSclass("black");
            }
            else
            {
                residueNumbers.setChecked(false);
                residueNumbers.setDisabled(true);
                residueNumbers.setSclass("gray");
            }

            //Sequential checkbox
            if (format.equals("PHYLIP"))
            {
                if (program.equals("TCS"))
                {
                    sequential.setChecked(true);
                    sequential.setDisabled(true);
                    sequential.setSclass("gray");
                }
                else if (program.equals("CodABC"))
                {
                    sequential.setChecked(true);
                    sequential.setDisabled(true);
                    sequential.setSclass("gray");
                }
                else if (program.equals("SeaView"))
                {
                    sequential.setChecked(false);
                    sequential.setDisabled(true);
                    sequential.setSclass("gray");
                }
                else
                {
                    sequential.setDisabled(false);
                    sequential.setSclass("black");
                }
            }
            else if (format.equals("NEXUS"))
            {

                if (program.equals("PAML") || program.equals("ProtTest")
                        || program.equals("TCS"))
                {
                    sequential.setChecked(true);
                    sequential.setDisabled(true);
                    sequential.setSclass("gray");
                }
                else if (program.equals("MEGA") || program.equals("trimAl"))
                {
                    sequential.setChecked(false);
                    sequential.setDisabled(true);
                    sequential.setSclass("gray");
                }
                else
                {
                    sequential.setDisabled(false);
                    sequential.setSclass("black");
                }
            }
            else
            {
                sequential.setChecked(false);
                sequential.setDisabled(true);
                sequential.setSclass("gray");
            }

            //Match checkbox
            if (format.equals("MSF") || program.equals("MrBayes")
                    || program.equals("SplitsTree") || program.equals("Clustal")
                    || program.equals("MAFFT") || program.equals("Gblocks")
                    || (program.equals("dnaSP") && format.equals("NEXUS")))
            {
                match.setChecked(false);
                match.setDisabled(true);
                match.setSclass("gray");
            }
            else
            {
                match.setDisabled(false);
                match.setSclass("black");
            }
        }
        else
        {
            lowerCase.setDisabled(true);
            lowerCase.setSclass("gray");
            match.setDisabled(true);
            match.setSclass("gray");
            residueNumbers.setDisabled(true);
            residueNumbers.setSclass("gray");
            sequential.setDisabled(true);
            sequential.setSclass("gray");
            collapse.setDisabled(true);
            collapse.setSclass("gray");
            ((Spinner) getFellow("limit")).setDisabled(true);
            ((Label) getFellow("limitLabel")).setSclass("gray");
            ((Checkbox) getFellow("gapsAsMissing")).setDisabled(true);
            ((Checkbox) getFellow("gapsAsMissing")).setSclass("gray");
            ((Checkbox) getFellow("countMissing")).setDisabled(true);
            ((Checkbox) getFellow("countMissing")).setSclass("gray");
            convert.setDisabled(true);
            andConvert.setSclass("gray");
        }
    }

    public void convert() throws InterruptedException
    {
        boolean collapse = ((Checkbox) getFellow("collapse")).isChecked();
        boolean gapsAsMissing = ((Checkbox) getFellow("gapsAsMissing")).isChecked();
        boolean countMissing = ((Checkbox) getFellow("countMissing")).isChecked();
        int limit = ((Spinner) getFellow("limit")).getValue().intValue();

        String out = "";
        String outO = "Linux";
        String outP = ((Listbox) getFellow("outputProgram")).getSelectedItem().getLabel().substring(4);
        String outF = ((Listbox) getFellow("outputFormat")).getSelectedItem().getLabel();
        ext = outF;

        boolean lower = ((Checkbox) getFellow("lower")).isChecked();
        boolean numbers = ((Checkbox) getFellow("numbers")).isChecked();
        boolean sequential = ((Checkbox) getFellow("sequential")).isChecked();
        boolean match = ((Checkbox) getFellow("match")).isChecked();

        //Get converter and convert
        Factory factory = new DefaultFactory();
        Writer writer;
        try
        {
            writer = factory.getWriter(outO, outP, outF, lower, numbers, sequential, match, logger.getName());
            if (collapse)
            {
                logger.log(Level.INFO, "***[haplotype collapse begin]***");
                MSA col = msa.collapse(gapsAsMissing, countMissing, limit, logger.getName());
                logger.log(Level.INFO, "***[haplotype collapse end]***");
                out = writer.write(col);
                collapsed = true;
            }
            else
            {
                out = writer.write(msa);
                collapsed = false;
            }
        }
        catch (Exception ex)
        {
            logger.log(Level.SEVERE, ex.getMessage());
            return;
        }

        ((Textbox) getFellow("output")).setDisabled(false);
        ((Textbox) getFellow("output")).setFocus(true);
        ((Listbox) getFellow("outputOS")).setDisabled(false);
        ((Label) getFellow("outputOSLabel")).setSclass("black");
        ((Html) getFellow("andSave")).setSclass("black");
        ((Button) getFellow("save")).setDisabled(false);
        ((Center) getFellow("step4")).setZclass("step4");

        outputText = new StringBuffer(out);
        if (out.length() < maxChars)
            ((Textbox) getFellow("output")).setRawValue(out);
        else
        {
            ((Textbox) getFellow("output")).setRawValue(outputText.substring(0, maxChars));
            Messagebox.show("The complete output text is not being displayed due to its length. Save the text as a file to get the complete text.",
                    "Output text too long", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    public void read()
    {
        String in = inputText.toString();
        Textbox input = (Textbox) getFellow("input");

        if (in.length() == input.getText().length())
            input.setReadonly(false);
        else
            input.setReadonly(true);

        if(!in.equals(""))
        {
            //Imprimir información de nuevo MSA
            logger.log(Level.INFO, "***[NEW MSA ENTERED]***");
            logger.log(Level.WARNING, "***[NEW MSA ENTERED]***");
            logger.log(Level.SEVERE, "***[NEW MSA ENTERED]***");

            //Deshabilitar pasos 3 y 4 y borrar textbox de salida
            ((North) getFellow("step3")).setZclass("step3bw");
            ((Listbox) getFellow("outputProgram")).setDisabled(true);
            ((Label) getFellow("outputProgramLabel")).setSclass("gray");
            ((Listbox) getFellow("outputProgram")).setSelectedIndex(0);
            onOutputProgramSelect();
            ((Center) getFellow("step4")).setZclass("step4bw");
            ((Textbox) getFellow("output")).setRawValue("");
            ((Textbox) getFellow("output")).setDisabled(true);
            ((Label) getFellow("outputOSLabel")).setSclass("gray");
            ((Listbox) getFellow("outputOS")).setDisabled(true);
            ((Html) getFellow("andSave")).setSclass("gray");
            ((Button) getFellow("save")).setDisabled(true);

            String inO;
            Listitem inOItem = ((Listbox) getFellow("inputOS")).getSelectedItem();
            if (inOItem == null)
                inOItem = ((Listbox) getFellow("inputOS")).getItemAtIndex(0);
            inO = (String)inOItem.getValue();
            boolean autodetect = ((Checkbox) getFellow("autodetect")).isChecked();
            String inP;
            String inF;
            if (autodetect)
            {
                inP = null;
                inF = null;
            }
            else
            {
                inP = ((Listbox) getFellow("inputProgram")).getSelectedItem().getLabel();
                inF = ((Listbox) getFellow("inputFormat")).getSelectedItem().getLabel();
            }

            //Get reader and read
            Factory factory = new DefaultFactory();
            Reader reader;
            try
            {
                reader = factory.getReader(inO, inP, inF, autodetect, logger.getName());
                msa = reader.read(in);
            }
            catch (ParseException ex)
            {
                logger.log(Level.SEVERE, "Failure parsing input MSA:\n" + ex.getMessage());
                return;
            }
            catch (UnsupportedOperationException ex)
            {
                logger.log(Level.SEVERE, ex.getMessage());
                return;
            }
            catch (Exception ex)
            {
                logger.log(Level.SEVERE, ex.getMessage());
                return;
            }

            ((Listbox) getFellow("outputProgram")).setDisabled(false);
            ((Label) getFellow("outputProgramLabel")).setSclass("black");
            onOutputProgramSelect();
            ((North) getFellow("step3")).setZclass("step3");
        }
    }

    public void onPanelOpen()
    {
        North north = (North) getFellow("step3");
        north.invalidate();
    }

    public void onPasteSample()
    {
        Textbox input = (Textbox) getFellow("input");
        boolean autodetect = ((Checkbox) getFellow("autodetect")).isChecked();

        if (autodetect)
        {
            input.setRawValue(stringFromResource("/clustal.nex"));
        }
        else
        {
            String program = ((Listbox) getFellow("inputProgram")).getSelectedItem().getLabel();
            String format = ((Listbox) getFellow("inputFormat")).getSelectedItem().getLabel();

            if (program.equals("Clustal"))
            {
                if (format.equals("ALN"))
                    input.setRawValue(stringFromResource("/clustal.aln"));
                else if (format.equals("FASTA"))
                    input.setRawValue(stringFromResource("/clustal.fas"));
                else if (format.equals("GDE"))
                    input.setRawValue(stringFromResource("/clustal.gde"));
                else if (format.equals("MSF"))
                    input.setRawValue(stringFromResource("/clustal.msf"));
                else if (format.equals("NEXUS"))
                    input.setRawValue(stringFromResource("/clustal.nex"));
                else if (format.equals("PHYLIP"))
                    input.setRawValue(stringFromResource("/clustal.phy"));
                else if (format.equals("PIR"))
                    input.setRawValue(stringFromResource("/clustal.pir"));
            }
            else if (program.equals("MAFFT"))
            {
                if (format.equals("ALN"))
                    input.setRawValue(stringFromResource("/mafft.aln"));
                else if (format.equals("FASTA"))
                    input.setRawValue(stringFromResource("/mafft.fas"));
            }
            else if (program.equals("TCoffee"))
            {
                if (format.equals("ALN"))
                    input.setRawValue(stringFromResource("/tcoffee.aln"));
                else if (format.equals("FASTA"))
                    input.setRawValue(stringFromResource("/tcoffee.fas"));
                else if (format.equals("MSF"))
                    input.setRawValue(stringFromResource("/tcoffee.msf"));
                else if (format.equals("PHYLIP"))
                    input.setRawValue(stringFromResource("/tcoffee.phy"));
                else if (format.equals("PIR"))
                    input.setRawValue(stringFromResource("/tcoffee.pir"));
            }
            else if (program.equals("MUSCLE"))
            {
                if (format.equals("ALN"))
                    input.setRawValue(stringFromResource("/muscle.aln"));
                else if (format.equals("FASTA"))
                    input.setRawValue(stringFromResource("/muscle.fas"));
                else if (format.equals("MSF"))
                    input.setRawValue(stringFromResource("/muscle.msf"));
                else if (format.equals("PHYLIP"))
                    input.setRawValue(stringFromResource("/muscle.phy"));
            }
            else if (program.equals("PROBCONS"))
            {
                if (format.equals("ALN"))
                    input.setRawValue(stringFromResource("/probcons.aln"));
                else if (format.equals("FASTA"))
                    input.setRawValue(stringFromResource("/probcons.fas"));
            }
        }

        inputText = new StringBuffer(input.getText());
        input.setFocus(true);
        read();
    }
    
    public String stringFromResource(String name)
    {       
        StringBuffer result = new StringBuffer();
        try
        {
            InputStream is = this.getClass().getResourceAsStream(name);
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String temp = "";
            while ((temp = in.readLine()) != null)
                result.append(temp + "\n");
            in.close();
        }
        catch (Exception e)
        {
            logger.log(Level.SEVERE,e.toString());
        }
        return result.toString();
     } 

    public void onInfoSave()
    {
        Filedownload.save(((Textbox) getFellow("info")).getText(), "text/plain", "info.txt");
    }

    public void onErrorSave()
    {
        Filedownload.save(((Textbox) getFellow("error")).getText(), "text/plain", "error.txt");
    }

    public void onWarningSave()
    {
        Filedownload.save(((Textbox) getFellow("warning")).getText(), "text/plain", "warning.txt");
    }

    public void onInfoClear()
    {
        ((Textbox) getFellow("info")).setRawValue("");
    }

    public void onErrorClear()
    {
        ((Textbox) getFellow("error")).setRawValue("");
    }

    public void onWarningClear()
    {
        ((Textbox) getFellow("warning")).setRawValue("");
    }

    public void initialize()
    {
        HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();

        String id = request.getSession().getId();
        logger = Logger.getLogger(id + System.currentTimeMillis());
        logger.setLevel(Level.ALL);

        WebLogHandler handler = new WebLogHandler((Textbox) getFellow("error"),
                (Textbox) getFellow("info"),(Textbox) getFellow("warning"));
        logger.addHandler(handler);

        Listbox inputOS = (Listbox) getFellow("inputOS");
        Listbox outputOS = (Listbox) getFellow("outputOS");

        
        String userAgent = request.getHeader("user-agent");

        if (userAgent.contains("WIN") || userAgent.contains("win") || userAgent.contains("Win"))
        {
            inputOS.getItemAtIndex(0).setValue("Windows");
            inputOS.getItemAtIndex(1).setValue("Linux");
            inputOS.getItemAtIndex(2).setValue("MacOS");
            inputOS.getItemAtIndex(0).setLabel("Windows");
            inputOS.getItemAtIndex(1).setLabel("Linux/Mac OS X");
            inputOS.getItemAtIndex(2).setLabel("Mac OS");
            outputOS.getItemAtIndex(0).setValue("Windows");
            outputOS.getItemAtIndex(1).setValue("Linux");
            outputOS.getItemAtIndex(2).setValue("MacOS");
            outputOS.getItemAtIndex(0).setLabel("Windows");
            outputOS.getItemAtIndex(1).setLabel("Linux/Mac OS X");
            outputOS.getItemAtIndex(2).setLabel("Mac OS");
        }
    }
    
    public void sendEmail(){
	String email = ((Textbox) getFellow("email")).getText();
	String subject = ((Textbox) getFellow("subject")).getText();
	String emailbody = ((Textbox) getFellow("emailbody")).getText();
	String attachment = ((Label) getFellow("attachmentTempLabel")).getValue();
	String attachmentName = ((Label) getFellow("attachmentLabel")).getValue();
	if (!attachment.equals("")){
	  Emailer.sendEmail(email, "alter@sing.ei.uvigo.es", "[ALTER-FEEDBACK] // "+subject, emailbody, attachment, attachmentName);
	}else{
	  Emailer.sendEmail(email, "alter@sing.ei.uvigo.es", "[ALTER-FEEDBACK] // "+subject, emailbody);
	}
	((Textbox) getFellow("subject")).setText("");
	((Textbox) getFellow("emailbody")).setText("");
	((Label) getFellow("attachmentTempLabel")).setValue("");
	((Label) getFellow("attachmentLabel")).setValue("");
    }
}
