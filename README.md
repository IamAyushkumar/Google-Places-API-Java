# Google-Places-API-Java
This code can be used for scraping data i.e. Autocomplete and Geocoding details.
  
                                                 HOW TO USE
Give the path of the input file which can be a file of companies or area names and desired name of the output file.

                                                  WORKING
 1.)AutoComplete:-
 
 Code for the same reads the data from the input files Line by Line.Then, complete the url using this data and google API key and obtains the result from the JSON after hitting this URL on the web and fills these result for the individual data in a List and prints it in an output File (whose name will be given as input by the client).
 
 2.) GeoCoding :- 
 
 This code takes the output of the autocomplete file as the input and does almost the same work as the Autocomplete, and the ouput file will contain latitude longitude for any input read from input file, And puts these result in a list and prints it in an output file.
 
                                       
=> As this code uses google API keys which may exhaust after few thousands hits. If any case like this occurs wait for 24 hrs to get the keys refreshed again.
