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

# Building ALTER and running from source

## Build
Before starting, you have to download and install:
1. Git tool for cloning the last version
2. A Java Compiler and tool
3. The Maven tool

And now, you can download and build it by performing:

```
git clone https://github.com/sing-group/ALTER.git
cd ALTER
mvn package
```

## Running the desktop graphical user interface
```
java -jar alter-lib/target/ALTER-1.3.4-jar-with-dependencies.jar
```

## Running the command line user interface
```
java -jar alter-lib/target/ALTER-1.3.4-jar-with-dependencies.jar help

```

## Running the web user interface in your own machine
1. Download a jetty-runner.jar, which is a server of Java Web applications. For example, download the 9.4.0RC1 version from here [here](http://central.maven.org/maven2/org/eclipse/jetty/jetty-runner/9.4.0.RC1/jetty-runner-9.4.0.RC1.jar)
2. Place the jetty-runner-9.4.0.RC1.jar inside your ALTER folder, and the run:
```
java -jar jetty-runner-9.4.0.RC1.jar alter-web/target/alter-web-1.3.4.war
```

Once the server has started, surf to [http://localhost:8080](http://localhost:8080/)

# Use the core library in your projects

## Include the library in your project

### Maven project (recommended)
Import our repository to your pom.xml file
```
<project>
    [...]
    <repositories>
        [...]
         <repository>
            <id>sing-repository</id>
            <name>SING repository</name>
            <url>http://sing.ei.uvigo.es/maven2</url>
        </repository>
        [...]
    </repositories>
    [...]
    <dependencies>
        [...]
    	<dependency>
            <groupId>es.uvigo.ei.sing</groupId>
            <artifactId>alter-lib</artifactId>
            <version>1.3.4</version>
        </dependency>
    	[...]
    </dependencies>
</project>
```



### Include the .jar inside your classpath
You have to include the alter-lib/target/ALTER-1.3.4-jar-with-dependencies.jar file in your classpath

## Make a sequence conversion inside your Java code
Here it is an example to convert a NEXUS file to ALN.

```java
package testalter;

import es.uvigo.ei.sing.alter.converter.Converter;
import es.uvigo.ei.sing.alter.converter.DefaultFactory;
import es.uvigo.ei.sing.alter.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestAlter {

	public static void main(String[] args) throws IOException {
		Converter converter = new DefaultFactory().getConverter(
            "Linux", 	// Input operating system (Linux, MacOS or Windows)
			"clustal", 	// Input program (Clustal, MAFFT, MUSCLE, PROBCONS or TCoffee)
			"NEXUS",	// Input format (ALN, FASTA, GDE, MEGA, MSF, NEXUS, PHYLIP or PIR)
			false,		// Autodetect format (other input options are omitted)
			false,		// Collapse sequences to haplotypes
			true,		// Treat gaps as missing data when collapsing
			false,		// Count missing data as differences when collapsing
			0,			// Connection limit (sequences differing at <= l sites will be collapsed) (default is l=0)
			"windows",	// Output operating system (Linux, MacOS or Windows)
			"general",	// Output program (jModelTest, MrBayes, PAML, PAUP, PhyML,
						// ProtTest, RAxML, TCS, CodABC,
						// BioEdit, MEGA, dnaSP, Se-Al, Mesquite, SplitsTree, Clustal, MAFFT,
						// MUSCLE, PROBCONS, TCoffee, Gblocks, SeaView, trimAl or GENERAL)
			"aln",  	// Output format (ALN, FASTA, GDE, MEGA, MSF, NEXUS, PHYLIP or PIR)
			false,		// Low case output
			false,		// Output residue numbers (only ALN format)
			false,		// Sequential output (only NEXUS and PHYLIP formats)
			false,		// Output match characters
			"MyConverterApp" // identifier for log messages
		);


		try {
			String inputSequence = new String(Files.readAllBytes(Paths.get("input.nexus")));

			String converted = converter.convert(inputSequence);

			System.out.println("converted file:");
			System.out.println(converted);

		} catch (ParseException e) {
			System.err.println("the input file seems to contain errors");
			e.printStackTrace();
		}

	}
}
```

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
