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

package es.uvigo.ei.sing.alter.gui;

import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingUtilities;

/**
 * Panel that contains the conversion options.
 * @author Daniel Gomez Blanco
 * @version 1.1
 */
public class ConvertPanel extends javax.swing.JPanel
{

    /**
     * Class constructor.
     */
    public ConvertPanel()
    {
        initComponents();
    }

    /**
     * Returns the selected input operating system.
     * @return Input operating system.
     */
    public String getInputOS()
    {
        return (String) inputOS.getItemAt(inputOS.getSelectedIndex());
    }

    /**
     * Returns the selected input program.
     * @return Input program.
     */
    public String getInputProgram()
    {
        return (String) inputProgram.getItemAt(inputProgram.getSelectedIndex());
    }

    /**
     * Returns the selected input format.
     * @return Input format.
     */
    public String getInputFormat()
    {
        return (String) inputFormat.getItemAt(inputFormat.getSelectedIndex());
    }

    /**
     * Returns the state of the autodetection option.
     * @return Autodetection checked or not.
     */
    public boolean getInputAutodetect()
    {
        return inputAutodetect.isSelected();
    }

    /**
     * Returns the state of the collapse option.
     * @return Collapse checked or not.
     */
    public boolean getCollapse()
    {
        return collapse.isSelected();
    }

    /**
     * Returns the limit to collapse.
     * @return Limit to collapse.
     */
    public int getCollapseLimit()
    {
        return ((Integer) collapseLimit.getModel().getValue()).intValue();
    }

    /**
     * Returns the state of treat gaps as missing data.
     * @return Treat gaps as missing data checked or not.
     */
    public boolean getCollapseGaps()
    {
        return collapseGaps.isSelected();
    }

    /**
     * Returns the state of count missing data as differences.
     * @return Count missing data as differences checked or not.
     */
    public boolean getCollapseMissing()
    {
        return collapseMissing.isSelected();
    }
    /**
     * Returns the selected output operating system.
     * @return Selected output operating system.
     */
    public String getOutputOS()
    {
        return (String) outputOS.getItemAt(outputOS.getSelectedIndex());
    }

    /**
     * Returns the selected output program.
     * @return Selected output program.
     */
    public String getOutputProgram()
    {
        return (String) outputProgram.getItemAt(outputProgram.getSelectedIndex());
    }

    /**
     * Returns the selected output format.
     * @return Selected output format.
     */
    public String getOutputFormat()
    {
        return (String) outputFormat.getItemAt(outputFormat.getSelectedIndex());
    }

    /**
     * Returns the state of lowercase output.
     * @return Lowercase output checked or not.
     */
    public boolean getOutputLowerCase()
    {
        return outputLowerCase.isSelected();
    }

    /**
     * Returns the state of output residue numbers.
     * @return Output residue numbers checked or not.
     */
    public boolean getOutputResidueNumbers()
    {
        return outputResidueNumbers.isSelected();
    }

    /**
     * Returns the state of sequential output.
     * @return Sequential output checked or not.
     */
    public boolean getOutputSequential()
    {
        return outputSequential.isSelected();
    }

    /**
     * Returns the state of output match characters.
     * @return Output match characters checked or not.
     */
    public boolean getOutputMatch()
    {
        return outputMatch.isSelected();
    }

    /** Method called in the constructor to initialize components.
     * This method was created by Netbeans IDE and it's not meant to be modified.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        inputPanel = new javax.swing.JPanel();
        inputOS = new javax.swing.JComboBox();
        inputOSLabel = new javax.swing.JLabel();
        inputProgramLabel = new javax.swing.JLabel();
        inputProgram = new javax.swing.JComboBox();
        inputFormatLabel = new javax.swing.JLabel();
        inputFormat = new javax.swing.JComboBox();
        inputAutodetect = new javax.swing.JCheckBox();
        outputPanel = new javax.swing.JPanel();
        outputProgramLabel = new javax.swing.JLabel();
        outputProgram = new javax.swing.JComboBox();
        outputFormatLabel = new javax.swing.JLabel();
        outputFormat = new javax.swing.JComboBox();
        outputLowerCase = new javax.swing.JCheckBox();
        outputResidueNumbers = new javax.swing.JCheckBox();
        outputOSLabel = new javax.swing.JLabel();
        outputOS = new javax.swing.JComboBox();
        outputSequential = new javax.swing.JCheckBox();
        outputMatch = new javax.swing.JCheckBox();
        collapsePanel = new javax.swing.JPanel();
        collapse = new javax.swing.JCheckBox();
        collapseGaps = new javax.swing.JCheckBox();
        collapseLimit = new javax.swing.JSpinner();
        collapseLimitLabel = new javax.swing.JLabel();
        collapseMissing = new javax.swing.JCheckBox();

        setName("Convert MSA"); // NOI18N

        inputPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Input"));

        inputOS.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Linux", "MacOS", "Windows" }));
        inputOS.setEnabled(false);

        inputOSLabel.setText("OS");
        inputOSLabel.setEnabled(false);

        inputProgramLabel.setText("Program");
        inputProgramLabel.setEnabled(false);

        inputProgram.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Clustal", "MAFFT", "MUSCLE", "PROBCONS", "TCoffee" }));
        inputProgram.setEnabled(false);
        inputProgram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputProgramActionPerformed(evt);
            }
        });

        inputFormatLabel.setText("Format");
        inputFormatLabel.setEnabled(false);

        inputFormat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ALN", "FASTA", "GDE", "MSF", "NEXUS", "PHYLIP", "PIR" }));
        inputFormat.setEnabled(false);

        inputAutodetect.setSelected(true);
        inputAutodetect.setText("Autodetect");
        inputAutodetect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputAutodetectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout inputPanelLayout = new javax.swing.GroupLayout(inputPanel);
        inputPanel.setLayout(inputPanelLayout);
        inputPanelLayout.setHorizontalGroup(
            inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inputPanelLayout.createSequentialGroup()
                        .addComponent(inputOSLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(inputProgramLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputProgram, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(inputFormatLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputFormat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(inputAutodetect))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        inputPanelLayout.setVerticalGroup(
            inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputPanelLayout.createSequentialGroup()
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputOSLabel)
                    .addComponent(inputOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inputProgramLabel)
                    .addComponent(inputProgram, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inputFormatLabel)
                    .addComponent(inputFormat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(inputAutodetect))
        );

        outputPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Output"));

        outputProgramLabel.setText("Program");

        outputProgram.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "GENERAL", "Clustal", "MAFFT", "MUSCLE", "PROBCONS", "TCoffee", "BioEdit", "Se-Al", "Gblocks", "trimAl", "jModelTest", "ProtTest", "MEGA", "Mesquite", "MrBayes", "PAML", "PAUP", "PhyML", "RAxML", "SeaView", "SplitsTree", "TCS", "dnaSP", "CodABC" }));
        outputProgram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outputProgramActionPerformed(evt);
            }
        });

        outputFormatLabel.setText("Format");

        outputFormat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ALN", "FASTA", "GDE", "MEGA", "MSF", "NEXUS", "PHYLIP", "PIR" }));
        outputFormat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outputFormatActionPerformed(evt);
            }
        });

        outputLowerCase.setText("Lower case");

        outputResidueNumbers.setText("Residue numbers");

        outputOSLabel.setText("OS");

        outputOS.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Linux", "MacOS", "Windows" }));

        outputSequential.setText("Sequential");
        outputSequential.setEnabled(false);

        outputMatch.setText("Match first");

        javax.swing.GroupLayout outputPanelLayout = new javax.swing.GroupLayout(outputPanel);
        outputPanel.setLayout(outputPanelLayout);
        outputPanelLayout.setHorizontalGroup(
            outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(outputPanelLayout.createSequentialGroup()
                        .addComponent(outputOSLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(outputOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(outputProgramLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(outputProgram, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(outputFormatLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(outputFormat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(outputPanelLayout.createSequentialGroup()
                        .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(outputResidueNumbers)
                            .addComponent(outputLowerCase))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(outputMatch)
                            .addComponent(outputSequential))))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        outputPanelLayout.setVerticalGroup(
            outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputPanelLayout.createSequentialGroup()
                .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(outputOSLabel)
                    .addComponent(outputOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outputProgramLabel)
                    .addComponent(outputProgram, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outputFormatLabel)
                    .addComponent(outputFormat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(outputLowerCase)
                    .addComponent(outputMatch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(outputResidueNumbers)
                    .addComponent(outputSequential)))
        );

        collapsePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Collapse"));

        collapse.setText("Collapse sequences to haplotypes");
        collapse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                collapseActionPerformed(evt);
            }
        });

        collapseGaps.setText("Treat gaps as missing data");
        collapseGaps.setEnabled(false);

        collapseLimit.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        collapseLimit.setEnabled(false);

        collapseLimitLabel.setText("Limit");
        collapseLimitLabel.setEnabled(false);

        collapseMissing.setSelected(true);
        collapseMissing.setText("Count missing data as differences");
        collapseMissing.setEnabled(false);

        javax.swing.GroupLayout collapsePanelLayout = new javax.swing.GroupLayout(collapsePanel);
        collapsePanel.setLayout(collapsePanelLayout);
        collapsePanelLayout.setHorizontalGroup(
            collapsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(collapsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(collapsePanelLayout.createSequentialGroup()
                        .addComponent(collapse)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(collapseLimitLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(collapseLimit, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                        .addGap(93, 93, 93))
                    .addGroup(collapsePanelLayout.createSequentialGroup()
                        .addComponent(collapseGaps)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(collapseMissing)))
                .addContainerGap())
        );
        collapsePanelLayout.setVerticalGroup(
            collapsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsePanelLayout.createSequentialGroup()
                .addGroup(collapsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(collapse)
                    .addComponent(collapseLimitLabel)
                    .addComponent(collapseLimit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(collapsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(collapseGaps)
                    .addComponent(collapseMissing)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(inputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(outputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(collapsePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(inputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(collapsePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Updates the supported output formats depending on the selected output program.
     * @param evt Event generated when selecting an output program.
     */
    private void outputProgramActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_outputProgramActionPerformed
    {//GEN-HEADEREND:event_outputProgramActionPerformed
        SwingUtilities.invokeLater(new Runnable()
        {

            public void run()
            {
                String program = (String) outputProgram.getItemAt(outputProgram.getSelectedIndex());
                if (program.equals("GENERAL"))
                    outputFormat.setModel(new DefaultComboBoxModel(new String[]
                            {
                                "ALN", "FASTA", "GDE", "MEGA", "MSF", "NEXUS", "PHYLIP", "PIR"
                            }));
                else if (program.equals("jModelTest"))
                    outputFormat.setModel(new DefaultComboBoxModel(new String[]
                            {
                                "ALN","FASTA", "MSF", "NEXUS", "PHYLIP", "PIR"
                            }));
                else if (program.equals("MrBayes"))
                    outputFormat.setModel(new DefaultComboBoxModel(new String[]
                            {
                                "NEXUS"
                            }));
                else if (program.equals("PAML"))
                    outputFormat.setModel(new DefaultComboBoxModel(new String[]
                            {
                                "NEXUS", "PHYLIP"
                            }));
                else if (program.equals("PAUP"))
                    outputFormat.setModel(new DefaultComboBoxModel(new String[]
                            {
                                "MEGA", "MSF", "NEXUS", "PHYLIP", "PIR"
                            }));
                else if (program.equals("PhyML"))
                    outputFormat.setModel(new DefaultComboBoxModel(new String[]
                            {
                                "PHYLIP"
                            }));
                else if (program.equals("ProtTest"))
                    outputFormat.setModel(new DefaultComboBoxModel(new String[]
                            {
                                "NEXUS","PHYLIP"
                            }));
                else if (program.equals("RAxML"))
                    outputFormat.setModel(new DefaultComboBoxModel(new String[]
                            {
                                "PHYLIP"
                            }));
                else if (program.equals("RAxML"))
                    outputFormat.setModel(new DefaultComboBoxModel(new String[]
                            {
                                "NEXUS", "PHYLIP"
                            }));
                else if (program.equals("TCS"))
                    outputFormat.setModel(new DefaultComboBoxModel(new String[]
                            {
                                "NEXUS", "PHYLIP"
                            }));
                else if (program.equals("CodABC"))
                    outputFormat.setModel(new DefaultComboBoxModel(new String[]
                            {
                                "PHYLIP"
                            }));
                else if (program.equals("BioEdit"))
                    outputFormat.setModel(new DefaultComboBoxModel(new String[]
                            {
                                "ALN","FASTA", "MSF", "NEXUS", "PHYLIP", "PIR"
                            }));
                else if (program.equals("MEGA"))
                    outputFormat.setModel(new DefaultComboBoxModel(new String[]
                            {
                                "ALN","FASTA", "MEGA","MSF", "NEXUS", "PHYLIP", "PIR"
                            }));
                else if (program.equals("dnaSP"))
                    outputFormat.setModel(new DefaultComboBoxModel(new String[]
                            {
                                "FASTA", "MEGA","NEXUS", "PHYLIP", "PIR"
                            }));
                else if (program.equals("Se-Al"))
                    outputFormat.setModel(new DefaultComboBoxModel(new String[]
                            {
                                "FASTA","GDE","NEXUS","PHYLIP","PIR"
                            }));
                else if (program.equals("Mesquite"))
                    outputFormat.setModel(new DefaultComboBoxModel(new String[]
                            {
                                "NEXUS"
                            }));
                else if (program.equals("SplitsTree"))
                    outputFormat.setModel(new DefaultComboBoxModel(new String[]
                            {
                                "ALN","FASTA","NEXUS","PHYLIP"
                            }));
                else if (program.equals("Clustal"))
                    outputFormat.setModel(new DefaultComboBoxModel(new String[]
                            {
                                "ALN","FASTA","GDE","MSF","PIR"
                            }));
                else if (program.equals("MAFFT"))
                    outputFormat.setModel(new DefaultComboBoxModel(new String[]
                            {
                                "FASTA"
                            }));
                else if (program.equals("MUSCLE"))
                    outputFormat.setModel(new DefaultComboBoxModel(new String[]
                            {
                                "FASTA"
                            }));
                else if (program.equals("PROBCONS"))
                    outputFormat.setModel(new DefaultComboBoxModel(new String[]
                            {
                                "FASTA"
                            }));
                else if (program.equals("TCoffee"))
                    outputFormat.setModel(new DefaultComboBoxModel(new String[]
                            {
                                "ALN","FASTA","MSF","PIR"
                            }));
                else if (program.equals("Gblocks"))
                    outputFormat.setModel(new DefaultComboBoxModel(new String[]
                            {
                                "FASTA","PIR"
                            }));
                else if (program.equals("SeaView"))
                    outputFormat.setModel(new DefaultComboBoxModel(new String[]
                            {
                                "ALN","FASTA","MSF","NEXUS","PHYLIP"
                            }));
                else if (program.equals("trimAl"))
                    outputFormat.setModel(new DefaultComboBoxModel(new String[]
                            {
                                "ALN","FASTA","MEGA","NEXUS","PHYLIP","PIR"
                            }));
            }
        });
        outputFormatActionPerformed(evt);
    }//GEN-LAST:event_outputProgramActionPerformed

    /**
     * Updates the supported output options depending on the selected output program and format.
     * @param evt Event generated when selecting an output format.
     */
    private void outputFormatActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_outputFormatActionPerformed
    {//GEN-HEADEREND:event_outputFormatActionPerformed
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                String format = (String) outputFormat.getItemAt(outputFormat.getSelectedIndex());
                String program = (String) outputProgram.getItemAt(outputProgram.getSelectedIndex());
                
                //Checkbox for residueNumbers
                if (format.equals("ALN"))
                {
                    outputResidueNumbers.setEnabled(true);
                }
                else
                {
                    outputResidueNumbers.setSelected(false);
                    outputResidueNumbers.setEnabled(false);
                }

                //Checkbox for sequential
                if (format.equals("PHYLIP"))
                {
                    if (program.equals("TCS"))
                    {
                        outputSequential.setSelected(true);
                        outputSequential.setEnabled(false);
                    }
                    else if (program.equals("CodABC"))
                    {
                        outputSequential.setSelected(true);
                        outputSequential.setEnabled(false);
                    }	
                    else if (program.equals("SeaView"))
                    {
                        outputSequential.setSelected(false);
                        outputSequential.setEnabled(false);
                    }
                    else
                    {
                        outputSequential.setEnabled(true);
                    }
                }
                else if (format.equals("NEXUS"))
                {
                    
                    if (program.equals("PAML") || program.equals("ProtTest")
                            || program.equals("TCS"))
                    {
                        outputSequential.setSelected(true);
                        outputSequential.setEnabled(false);
                    }
                    else if (program.equals("MEGA") || program.equals("trimAl"))
                    {
                        outputSequential.setSelected(false);
                        outputSequential.setEnabled(false);
                    }
                    else
                    {
                        outputSequential.setEnabled(true);
                    }
                }
                else
                {
                    outputSequential.setSelected(false);
                    outputSequential.setEnabled(false);
                }

                //Checkbox for match
                if (format.equals("MSF") || program.equals("MrBayes")
                        || program.equals("SplitsTree") || program.equals("Clustal")
                        || program.equals("MAFFT") || program.equals("Gblocks")
                        || (program.equals("dnaSP") && format.equals("NEXUS")))
                {
                    outputMatch.setSelected(false);
                    outputMatch.setEnabled(false);
                }
                else
                {
                    outputMatch.setEnabled(true);
                }
            }
        });
    }//GEN-LAST:event_outputFormatActionPerformed

    /**
     * Disables input options in case autodetection is checked. Enables them in other case.
     * @param evt Event generated when checking or unchecking autodetection.
     */
    private void inputAutodetectActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_inputAutodetectActionPerformed
    {//GEN-HEADEREND:event_inputAutodetectActionPerformed
        SwingUtilities.invokeLater(new Runnable()
        {

            public void run()
            {
                if (inputAutodetect.isSelected())
                {
                    inputOSLabel.setEnabled(false);
                    inputOS.setEnabled(false);
                    inputProgramLabel.setEnabled(false);
                    inputProgram.setEnabled(false);
                    inputFormatLabel.setEnabled(false);
                    inputFormat.setEnabled(false);
                }
                else
                {
                    inputOSLabel.setEnabled(true);
                    inputOS.setEnabled(true);
                    inputProgramLabel.setEnabled(true);
                    inputProgram.setEnabled(true);
                    inputFormatLabel.setEnabled(true);
                    inputFormat.setEnabled(true);
                }
            }
        });
    }//GEN-LAST:event_inputAutodetectActionPerformed

    /**
     * Enables collapse options in case collapse is checked. Disables them in other case.
     * @param evt Event generated when checking or unchecking the collapse option.
     */
    private void collapseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_collapseActionPerformed
    {//GEN-HEADEREND:event_collapseActionPerformed
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                if (collapse.isSelected())
                {
                    collapseLimitLabel.setEnabled(true);
                    collapseLimit.setEnabled(true);
                    collapseGaps.setEnabled(true);
                    collapseMissing.setEnabled(true);
                }
                else
                {
                    collapseLimitLabel.setEnabled(false);
                    collapseLimit.setEnabled(false);
                    collapseGaps.setEnabled(false);
                    collapseMissing.setEnabled(false);
                }
            }
        });
    }//GEN-LAST:event_collapseActionPerformed

    /**
     * Updates the supported input formats when selecting an input program.
     * @param evt Event generated when selecting an input program.
     */
    private void inputProgramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputProgramActionPerformed
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                String program = (String) inputProgram.getItemAt(inputProgram.getSelectedIndex());
                if (program.equals("Clustal"))
                    inputFormat.setModel(new DefaultComboBoxModel(new String[]
                            {
                                "ALN", "FASTA", "GDE", "MSF", "NEXUS", "PHYLIP", "PIR"
                            }));
                else if (program.equals("MAFFT"))
                    inputFormat.setModel(new DefaultComboBoxModel(new String[]
                            {
                                "ALN", "FASTA"
                            }));
                else if (program.equals("MUSCLE"))
                    inputFormat.setModel(new DefaultComboBoxModel(new String[]
                            {
                                "ALN", "FASTA", "MSF", "PHYLIP"
                            }));
                else if (program.equals("PROBCONS"))
                    inputFormat.setModel(new DefaultComboBoxModel(new String[]
                            {
                                "ALN", "FASTA"
                            }));
                else if (program.equals("TCoffee"))
                    inputFormat.setModel(new DefaultComboBoxModel(new String[]
                            {
                                "ALN", "FASTA", "MSF", "PHYLIP", "PIR"
                            }));
            }
        });
    }//GEN-LAST:event_inputProgramActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox collapse;
    private javax.swing.JCheckBox collapseGaps;
    private javax.swing.JSpinner collapseLimit;
    private javax.swing.JLabel collapseLimitLabel;
    private javax.swing.JCheckBox collapseMissing;
    private javax.swing.JPanel collapsePanel;
    private javax.swing.JCheckBox inputAutodetect;
    private javax.swing.JComboBox inputFormat;
    private javax.swing.JLabel inputFormatLabel;
    private javax.swing.JComboBox inputOS;
    private javax.swing.JLabel inputOSLabel;
    private javax.swing.JPanel inputPanel;
    private javax.swing.JComboBox inputProgram;
    private javax.swing.JLabel inputProgramLabel;
    private javax.swing.JComboBox outputFormat;
    private javax.swing.JLabel outputFormatLabel;
    private javax.swing.JCheckBox outputLowerCase;
    private javax.swing.JCheckBox outputMatch;
    private javax.swing.JComboBox outputOS;
    private javax.swing.JLabel outputOSLabel;
    private javax.swing.JPanel outputPanel;
    private javax.swing.JComboBox outputProgram;
    private javax.swing.JLabel outputProgramLabel;
    private javax.swing.JCheckBox outputResidueNumbers;
    private javax.swing.JCheckBox outputSequential;
    // End of variables declaration//GEN-END:variables
}
