ALTER Project
=============

# What is ALTER
ALTER stands for (ALignment Transformation EnviRonment) and it is a set of software components to transform between [Multiple Sequence Alignment](http://en.wikipedia.org/wiki/Multiple_sequence_alignment) file formats.

ALTER difers from other similar tools in the way that is "program-oriented" instead of simply "format-oriented", that is, when you convert between one format into another, you have to specify which program did generate your input file and which program will consume your generated file. This is because many programs does not follow the exact format standard, making custom asumptions about it, so tranforming directly between standard formats does not always ensure that your produced file will be processed by the next program correctly.

# ALTER software components
ALTER contains a set of components:

1. A core library.
2. A command line interface.
3. A desktop graphical user interface (GUI).
4. A web interface (a running instance is here: [http://sing.ei.uvigo.es/alter](http://sing.ei.uvigo.es/alter)).

# Development Team
The ALTER development team is:

* Daniel Glez-Peña.
* Daniel Gómez-Blanco.
* Miguel Reboiro-Jato.
* Florentino Fdez-Riverola.
* David Posada.

# Citing ALTER
If you are using ALTER in your research work, please cite us:

D. Glez-Peña; D. Gómez-Blanco; M. Reboiro-Jato; F. Fdez-Riverola; D. Posada (2010) ALTER: program-oriented format conversion of DNA and protein alignments. Nucleic Acids Research. Web Server issue. ISSN: 0305-1048
[http://dx.doi.org/10.1093/nar/gkq321](http://dx.doi.org/10.1093/nar/gkq321)
