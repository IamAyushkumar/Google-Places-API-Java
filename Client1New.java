import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import gvjava.org.json.JSONArray;
import gvjava.org.json.JSONObject;

//pranav - AIzaSyAG-Wny30wnb7_RQCQDP6t-OPCIbzXyags
//ASH - AIzaSyBMyEfQtWQ_7-L9-Al-9A5RXag_wUuXUe8
//abc - AIzaSyBomu_KPYPfzc8uY092XAHGLGIT1oxiEG0
//axyz6911 - AIzaSyA-ZzSWIvwLLXN5P3VCSqv4ImD2Okla27I

@SuppressWarnings("unchecked")
public class Client1New {
	private static final String[] REGION_ARRAY = { "a_Delhi India", "a_Gurgaon India", "a_Noida India" };
	private static final String INPUT_FOLDER_BASE_ADDRESS = "/Users/ayush/Documents/workspace/Sarathi-2/All Area Files/";
	private static final String FOLDER_BASE_ADDRESS = "/Users/ayush/Documents/workspace/Sarathi-2/All Area Files/";
	private static final int MAX_REGION_LIST_SIZE = 10;
	private static final String AUTO_COMPLETE_BASE_URL = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=";
	private static final String KEY = "&key=";
	private static final String GEO_CODING_BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=";
	private static final String PINCODE_BASE_URL = "http://www.getpincode.info/";
	private static final String COMMA = ",";
	private static final String newLine = System.getProperty("line.separator").toString();
	// private static final String[] keys = {
	// "AIzaSyCs3AZblGiGqcHaSnuc0sK6-X8KEclqIm0",
	// "AIzaSyDIy9QcL8I7yMtDMDFEk8bhVayJ_Njahao",
	// "AIzaSyBsg_VMQll-oLiMdS5hk37ZbCc-0rAVj2g",
	// "AIzaSyBDAOJS0-XK8NeGX_rCwCopyBDE1F2CaXg",
	// "AIzaSyCcLSHTg8o-TVqO2hUzPQjQOjzT462QOzs",
	// "AIzaSyBoWjwMsI6-aYKF8shlYmfrFF_HlnxuGZk",
	// "AIzaSyAiK38Ly0xMbxqb5Lj2VqQHrCqnMJtCmaU" };

	private static final String[] keys = { "AIzaSyAuIGg2cJl1jk9hMEVYjhnEj2SfmXAwqw8",
			"AIzaSyBxpHwu3T6QRjkiBaK0H_zp8sTpCNSv0JI", "AIzaSyA2u-lKLJSqYNU7kP-oaL4gC5V8AwNdma8",
  			"AIzaSyBC_9IVXtkp2BkbbsCQv1jWuWFucyhmY_0", "AIzaSyAiK38Ly0xMbxqb5Lj2VqQHrCqnMJtCmaU",
			"AIzaSyBoWjwMsI6-aYKF8shlYmfrFF_HlnxuGZk", "AIzaSyCcLSHTg8o-TVqO2hUzPQjQOjzT462QOzs",
			"AIzaSyBDAOJS0-XK8NeGX_rCwCopyBDE1F2CaXg", "AIzaSyBsg_VMQll-oLiMdS5hk37ZbCc-0rAVj2g",
			"AIzaSyDIy9QcL8I7yMtDMDFEk8bhVayJ_Njahao", "AIzaSyCs3AZblGiGqcHaSnuc0sK6-X8KEclqIm0",
			"AIzaSyA-ZzSWIvwLLXN5P3VCSqv4ImD2Okla27I", "AIzaSyBMyEfQtWQ_7-L9-Al-9A5RXag_wUuXUe8",
			"AIzaSyBomu_KPYPfzc8uY092XAHGLGIT1oxiEG0", "AIzaSyAG-Wny30wnb7_RQCQDP6t-OPCIbzXyags",
			"AIzaSyBuOFY83ybcWcxjXK3Uw1Qdw15vmCZ8chk", "AIzaSyA2u-lKLJSqYNU7kP-oaL4gC5V8AwNdma8",
			"AIzaSyDq_m2Fiyri70hPvPkOg08-ocEeQatY6A4", "AIzaSyBC_9IVXtkp2BkbbsCQv1jWuWFucyhmY_0",
			"AIzaSyA9uWc1z_-MYiHX207twWMjeaqDM8yKH-Y", "AIzaSyDNRwkpd_qLpe0VdyVOoVmM39wGAgzfZ4Y" };
	
	private static int currentKeyIndex = 0;

	private static class CompanyInfo {
		List<String> companyDescriptionList;

		List<String>[] regionalCompanyDetailsList;

		Set<String> allRegionCompanyDetails;

		private CompanyInfo() {
			companyDescriptionList = new ArrayList<>();

			allRegionCompanyDetails = new HashSet<>();

			regionalCompanyDetailsList = new List[MAX_REGION_LIST_SIZE];
		}

	}

	private static class LatLng {
		Double latitude;
		Double longitude;

		private LatLng() {

		}

		private LatLng(Double latitude, Double longitude) {
			this.latitude = latitude;
			this.longitude = longitude;
		}
	}

	public static void main(String[] Args) throws Exception {

		String inputCompanyNameListFileName = "a_naukri.txt";
		String inputAddressListFileName = "FinalAreaFile.txt";
		String outputFileName = "FinalAreaFilewithLocation.txt";

		// Produce Intermediate Files and overall Address Files
		//printAllRegionsAddress(inputCompanyNameListFileName, inputAddressListFileName);

		// Now get geoLocation of Address List in overall Address File
		GeocodingReadPrint(inputAddressListFileName, outputFileName);

	}

	public static void printAllRegionsAddress(String inputCompanyNameListFileName, String inputAddressListFileName)
			throws Exception {

		if (REGION_ARRAY.length > MAX_REGION_LIST_SIZE) {

			System.err.println("you exceeded the permissible limit of regions");

		}

		HashMap<String, CompanyInfo> companyDetailsMap = new HashMap<>();

		List<String> CompanyNamesList = new ArrayList<>();

		CompanyNamesList = readTextFileByLines(INPUT_FOLDER_BASE_ADDRESS + inputCompanyNameListFileName);

		if (null == CompanyNamesList) {
			System.err.println(inputAddressListFileName + " can't be read or it is empty");
		}

		InitialiseSystem(REGION_ARRAY);

		boolean setBreakFromouterLoop = false;

		int lastProceesedregionNumber = 0;

		int lastProceesedCompanyNameIndex = 0;

		Path path = Paths
				.get(FOLDER_BASE_ADDRESS + "lastCompanyProcessedinAutoComplete" + inputAddressListFileName + " .txt");

		if (Files.exists(path)) {
			List<String> ListOFlastCompanyProcesssed = readTextFileByLines(
					FOLDER_BASE_ADDRESS + "lastCompanyProcessedinAutoComplete" + inputAddressListFileName + " .txt");

			String LastCompanytakenFromList = ListOFlastCompanyProcesssed.get(ListOFlastCompanyProcesssed.size() - 1);

			String lastProcessedcompanyName = LastCompanytakenFromList.substring(0,
					LastCompanytakenFromList.indexOf("|"));

			String regionStringReadfromautoCompFile = LastCompanytakenFromList
					.substring(LastCompanytakenFromList.indexOf("|") + 1);

			lastProceesedCompanyNameIndex = CompanyNamesList.indexOf(lastProcessedcompanyName);
			lastProceesedregionNumber = Integer.parseInt(regionStringReadfromautoCompFile);

		}

		for (int i = lastProceesedCompanyNameIndex; i < CompanyNamesList.size(); i++) {

			String companyName = CompanyNamesList.get(i);

			CompanyInfo companyInfo = new CompanyInfo();

			if (null == companyName) {
				System.err.println("printAllRegionsAddress: Company name found to be null");
			}

			if ("" == companyName) {
				System.err.println("printAllRegionsAddress: comapany name found to be blank.Please recheck");
			}

			if (null == companyInfo) {
				System.err.println("printAllRegionsAddress: something bad hapened");

			}

			String regionalCompanyName = null;
			boolean status = true;
			for (int regionNumber = lastProceesedregionNumber; regionNumber < REGION_ARRAY.length; regionNumber++) {
				// String Listname = companyName + REGION_ARRAY[region] +
				// "Description List";
				regionalCompanyName = companyName + REGION_ARRAY[regionNumber];

				List<String> CompanyRegionDescList = getAutoComplete(regionalCompanyName, keys, status);

				// check if keys exsusted and break from both for loops to come
				// out and print

				if (currentKeyIndex == keys.length || false == status) {
					if (false == status) {
						System.err.println("printAllRegionsAddress: Google Denied request, may be for this IP.");

					} else {
						System.err.println(
								"printAllRegionsAddress: Keys got exhausted. Return after 24 hours again for further information");

					}

					File file = new File(FOLDER_BASE_ADDRESS + "lastCompanyProcessedinAutoComplete"
							+ inputAddressListFileName + " .txt");
					if (file.exists()) {
						System.out.println("lastCompanyProcessedinAutoComplete" + inputAddressListFileName
								+ " .txt file created!!!!");
					}

					else {

						System.err.println("lastCompanyProcessedinAutoComplete" + inputAddressListFileName
								+ " .txt already exists!!!");
					}

					PrintWriter pw = new PrintWriter(FOLDER_BASE_ADDRESS + "lastCompanyProcessedinGeoCoding.txt");
					pw.print(companyName + "|" + regionNumber);

					setBreakFromouterLoop = true;
					break;
				}

				companyInfo.regionalCompanyDetailsList[regionNumber] = CompanyRegionDescList;

				for (String companyDesc : CompanyRegionDescList) {
					if (null == companyDesc) {
						System.out.println("printAllRegionsAddress: companyDesc is null for companyName and region"
								+ REGION_ARRAY[regionNumber]);
						continue;

					}
					companyInfo.allRegionCompanyDetails.add(companyDesc);
				}

			}

			if (true == setBreakFromouterLoop) {
				break;
			}

			if (null == companyInfo) {
				System.err.println("printAllRegionsAddress: companyInfo is null");

			}
			companyDetailsMap.put(companyName, companyInfo);

		}

		// Print individual Regions Info

		printRegionInfo(companyDetailsMap, CompanyNamesList);

		// Now print For all Regions
		printAllregions(companyDetailsMap, CompanyNamesList, inputAddressListFileName);
	}

	public static void GeocodingReadPrint(String inputAddressListFileName, String outputFileName) throws IOException {

		/***** Now we have all area address: Geo Coding Start here *****/

		List<String> companyDescFromNCRfile = readTextFileByLines(FOLDER_BASE_ADDRESS + inputAddressListFileName);

		List<String> companyDescWithLatLng = getGeoCoding(companyDescFromNCRfile, inputAddressListFileName);

		File file = new File(FOLDER_BASE_ADDRESS + outputFileName);

		if (file.createNewFile()) {
			System.out.println(outputFileName + "File is created!");
		} else {
			System.err.println(outputFileName + "File already exists.");

		}

		PrintWriter pw1 = new PrintWriter(new FileWriter(FOLDER_BASE_ADDRESS + outputFileName));

		pw1.println("Description, Latitude, Longitude");

		for (String compDescLL : companyDescWithLatLng) {
			pw1.println(compDescLL);
		}

		pw1.flush();
		pw1.close();
	}

	public static void printRegionInfo(HashMap<String, CompanyInfo> companyDetailsMap, List<String> CompanyNamesList)
			throws Exception {
		CompanyInfo companyInfo;
		List<String> companyDescList;
		PrintWriter[] pwRegional = new PrintWriter[REGION_ARRAY.length];

		for (int regionNumber = 0; regionNumber < REGION_ARRAY.length; regionNumber++) {
			pwRegional[regionNumber] = new PrintWriter(
					new FileWriter(FOLDER_BASE_ADDRESS + REGION_ARRAY[regionNumber] + ".txt"));
		}

		for (String companyName : CompanyNamesList) {
			for (int regionNumber = 0; regionNumber < REGION_ARRAY.length; regionNumber++) {

				companyInfo = companyDetailsMap.get(companyName);
				companyDescList = companyInfo.regionalCompanyDetailsList[regionNumber];

				// Print List of Strings regionFile for this regionNumer
				for (String compDec : companyDescList) {
					pwRegional[regionNumber].append(compDec);
					pwRegional[regionNumber].append(newLine);

				}

			}
		}

		for (int regionNumber = 0; regionNumber < REGION_ARRAY.length; regionNumber++) {
			pwRegional[regionNumber].flush();
			pwRegional[regionNumber].close();
		}

	}

	public static void printAllregions(HashMap<String, CompanyInfo> companyDetailsMap, List<String> CompanyNamesList,
			String outputFileName) throws Exception {
		File file = new File(FOLDER_BASE_ADDRESS + outputFileName);

		if (file.createNewFile()) {
			System.out.println(outputFileName + "File is created!");
		} else {
			System.err.println(outputFileName + "File already exists.");

		}

		PrintWriter pw1 = new PrintWriter(new FileWriter(FOLDER_BASE_ADDRESS + outputFileName));

		CompanyInfo companyInfo;
		for (String company : CompanyNamesList) {
			companyInfo = companyDetailsMap.get(company);

			// Print Set List of Strings

			for (String regionCompanyDetails : companyInfo.allRegionCompanyDetails) {

				pw1.println(regionCompanyDetails);

			}
			pw1.flush();
		}

		pw1.close();

	}

	private static void InitialiseSystem(String[] REGION_ARRAY) {
		for (int region = 0; region < REGION_ARRAY.length; region++) {
			try {

				File file = new File(FOLDER_BASE_ADDRESS + REGION_ARRAY[region] + ".txt");

				if (file.createNewFile()) {
					System.out.println(FOLDER_BASE_ADDRESS + REGION_ARRAY[region] + ".txt" + "File is created!");
				} else {
					System.err.println(FOLDER_BASE_ADDRESS + REGION_ARRAY[region] + ".txt" + "File already exists.");
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	private static List<String> readTextFileByLines(String fileName) throws IOException {
		return Files.readAllLines(Paths.get(fileName));
	}

	private static List<String> getAutoComplete(String companyName, String[] keys, boolean status) {
		List<String> companyDescriptions = new ArrayList<>();
		// StringBuilder sb = new StringBuilder();
		status = true;

		try {
			while (currentKeyIndex < keys.length) {
				String strUrl = AUTO_COMPLETE_BASE_URL + companyName.trim().replaceAll(" ", "+") + KEY
						+ keys[currentKeyIndex];
				System.out.println(strUrl);
				URL url = new URL(strUrl);
				BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
				String strTemp = "";
				StringBuilder sb = new StringBuilder();
				while (null != (strTemp = br.readLine())) {
					sb.append(strTemp);
				}
				String stringjsonAfterHitting = sb.toString();

				if (stringjsonAfterHitting.contains("You have exceeded your daily request quota for this API.")) {

					System.out.println("getAutoComplete: key exhausted :" + currentKeyIndex + stringjsonAfterHitting);
					++currentKeyIndex;
					continue;

					// getAutoComplete(companyName, keys);
				}
				if (stringjsonAfterHitting.contains("This API project is not authorized to use this API.")) {

					System.out.println("getAutoComplete Error: This API project is not authorized to use this API. :"
							+ currentKeyIndex);
					status = false;
					break;
				}

				System.out.println(stringjsonAfterHitting);
				JSONObject jsobject = new JSONObject(stringjsonAfterHitting);
				JSONArray prediction = (JSONArray) jsobject.get("predictions");
				for (int desc = 0; desc < prediction.length(); desc++) {
					JSONObject description = ((JSONArray) jsobject.get("predictions")).getJSONObject(desc);
					String name = description.getString("description");
					companyDescriptions.add(name);
					// companyName + "," +
				}
				break;
			}

		} catch (Exception ex) {

			System.out.println(ex.getMessage());
			if (ex.getMessage().contains("You have exceeded your daily request quota for this API.")) {

				System.out.println("getAutoComplete Inside Exception, key exhausted :" + currentKeyIndex);

				++currentKeyIndex;

				getAutoComplete(companyName, keys, status);
			}
			ex.printStackTrace();
		}

		return companyDescriptions;
	}

	private static List<String> getGeoCoding(List<String> companyDescriptions, String inputAddressListFileName)
			throws IOException {

		Path path = Paths
				.get(FOLDER_BASE_ADDRESS + "lastCompanyProcessedinGeoCoding" + inputAddressListFileName + ".txt");

		int number = 0;

		if (Files.exists(path)) {
			List<String> ListOFlastCompanyProcesssed = readTextFileByLines(
					FOLDER_BASE_ADDRESS + "lastCompanyProcessedinGeoCoding" + inputAddressListFileName + ".txt");

			String LastCompanytakenFromList = ListOFlastCompanyProcesssed.get(ListOFlastCompanyProcesssed.size() - 1);

			number = companyDescriptions.indexOf(LastCompanytakenFromList);

		}

		List<String> comapanyDetailswithLatLng = new ArrayList<>();

		try {
			// while (currentKeyIndex < keys.length) {

			for (int i = number; i < companyDescriptions.size(); i++) {

				String companyDesc = companyDescriptions.get(i);

				StringBuilder sb = new StringBuilder();

				String strUrl = GEO_CODING_BASE_URL + companyDesc.trim().replaceAll(" ", "+").replaceAll(",", "%2C")
						+ KEY + keys[currentKeyIndex];

				URL url = new URL(strUrl);
				BufferedReader br = null;

				if (null != url) {

					br = new BufferedReader(new InputStreamReader(url.openStream()));

				}

				if (br == null) {
					System.err.println(" something bad happened and we need to terminate here");
				}
				String strTemp = "";

				while (null != (strTemp = br.readLine())) {

					sb.append(strTemp + '\n');
				}

				String stringjsonAfterHitting = sb.toString();

				if (stringjsonAfterHitting.contains("You have exceeded your daily request quota for this API.")
						|| stringjsonAfterHitting.contains("This API project is not authorized to use this API.")) {

					File file = new File(FOLDER_BASE_ADDRESS + "lastCompanyProcessedinGeoCoding"
							+ inputAddressListFileName + ".txt");

					if (file.exists()) {
						System.out.println(
								"lastCompanyProcessedinGeoCoding" + inputAddressListFileName + ".txt file created!!!!");
					}

					else {

						System.err.println("lastCompanyProcessedinGeoCoding" + inputAddressListFileName
								+ ".txt file already exists!!!");
					}

					PrintWriter pw = new PrintWriter(FOLDER_BASE_ADDRESS + "lastCompanyProcessedinGeoCoding.txt");
					pw.print(companyDesc);
					pw.flush();
					pw.close();
					++currentKeyIndex;
					i--; // we want current record to be reprocessed
					continue;

				}

				System.out.println(stringjsonAfterHitting);

				LatLng latlng = new LatLng();

				JSONObject jsobject = new JSONObject(sb.toString());
				if (null == jsobject || null == stringjsonAfterHitting
						|| ((JSONArray) (jsobject).get("results")).length() == 0) {
					System.err.println("geocoding : something bad happened");
				} else {
					latlng.latitude = ((JSONArray) (jsobject).get("results")).getJSONObject(0).getJSONObject("geometry")
							.getJSONObject("location").getDouble("lat");
					latlng.longitude = ((JSONArray) (jsobject).get("results")).getJSONObject(0)
							.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
					
					//ArrayList<Json> country = ((JSONArray) (jsobject).get("results")).getJSONObject(1).getJSONObject("address_components"); 
					
					
					
					//comapanyDetailswithLatLng.add(companyDesc + "," + latlng.latitude + "," + latlng.longitude);
				}
			}
			// break;
			//
			// }
		}

		catch (Exception ex) {
			if (ex.getMessage().contains("You have exceeded your daily request quota for this API.")) {

				System.out.println("getGeoCoding: Inside Exception,key exhausted :" + currentKeyIndex);

				++currentKeyIndex;

				getGeoCoding(companyDescriptions, inputAddressListFileName);
			}
			ex.printStackTrace();

		}

		return comapanyDetailswithLatLng;

	}
}