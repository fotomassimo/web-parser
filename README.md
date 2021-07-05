## WEB-PARSER

#General info 
The executable jar file "web-parser.jar" for this application can be found in the project folder "out/artifacts/web_parser_jar/". 
Application was developed using JDK 1.7. As per task requirements, none of the Java frameworks was used (even 
Collections and Stream API).  

The following  open source libraries was used:

* JSOUP v.1.13.1 to set up connection imitating regular browser and scrape data from inspected web page in JSON format; 
* GSON v.2.2.4 to parse data in JSON format and create Product objects;
* OPENCSV v.4.0 to write parsed data into files in CSV format.

The algorithm of parsing is based on iteration of the variable index in URL that is used by the given web page
to dynamically download subpages with JSON objects while scrolling it down. URL was retrieved after investigating
Network tab in Inspect mode of the given page in Chrome browser. The number of subpages used by the given page to 
display full content is 5. 

For purpose of code readability and to be able to change URL without effecting the code the retrieved URL was stored 
in the separate file "URL_with_variable_index.txt" in the project folder "src/main/resources/".
The extracted information is written to the file "output.csv" in the same folder where the jar file is launched.
Launching application from IDE will write the output file in the root directory of the project.
