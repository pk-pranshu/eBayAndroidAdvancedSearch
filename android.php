<?php
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, POST');

		

			error_reporting(E_ALL);  // Turn on all errors, warnings and notices for easier debugging

			// API request variables
			$endpoint = 'http://svcs.ebay.com/services/search/FindingService/v1';  // URL to call
			$version = '1.0.0';  // API version supported by your application
			$appid = 'Universi-f98a-4e94-bcb8-d1906bf5af9b';  // Replace with your own AppID
			$globalid = 'EBAY-US';  // Global ID of the eBay site you want to search (e.g., EBAY-DE)
			$query = $_GET['keywords'];  // You may want to supply your own query
			$safequery = urlencode($query);  // Make the query URL-friendly
			$responseEncoding='XML';
			$sortingOrder = $_GET['sort'];
			//$resultsPerPage = $_GET['results'];
			$pageNo=1;
			
			$i=0;
			$seller="";
			$lower="";
			$upper="";
			
			$days="";
			$condition="";
			$buyFormat="";
			$seller="";
			$freeship="";
			$expship="";
			$flag= "";


			if(($_GET['lower'])!="")
			{
				$lower= $_GET['lower'];
			}
			else{
				$lower = "-123";
			}


			if(($_GET['upper'])!="")
			{
				$upper= $_GET['upper'];
			}
			else
			{
				$upper = "-123";
			}

			// if(($_GET['condition'])!="")
			// {
			// 	$condition=$_GET['condition'];
			// }

			// if(($_GET['formats'])!="")
			// {
			// 	$buyFormat=$_GET['formats'];
			// }

			// if(($_GET['return'])!="")
			// {
			// 	//echo $_GET['return'];
			// 	$seller="true";
			// }
			// else 
			// 	{
			// 		//echo "not selected";
			// 		$seller="";
			// 	}

			// if(($_GET['shipping1'])!="")
			// {
			// 	$freeship="true";
			// }
			// else
			// {
			// 	$freeship = "";
			// }

			// if(($_GET['expship'])!="")
			// {
			// 	$expship="Expedited";
			// }
			// else $expship="";

			// if(($_GET['days'])!="")
			// {
			// 	$days=$_GET['days'];
			// }

			// if(($_GET['pageNumber'])!="")
			// {
			// 	$pageNo=$_GET['pageNumber'];
			// }
			// else
			// {

			// 	$pageNo=1;
			// }
			

			

			
	
			



			$filterarray =
		  array(
		  	 array(
		    'name' => 'MinPrice',
		    'value' => $lower,
		    'paramName' => 'Currency',
		    'paramValue' => 'USD'),
		    array(
		     'name' => 'MaxPrice',
		    'value' => $upper,
		    'paramName' => 'Currency',
		    'paramValue' => 'USD'),	   
		    array(    
		    'name' => 'Condition',
		    'value' => $condition,
		    'paramName' => '',
		    'paramValue' => ''),
		    array(
		    'name' => 'ListingType',
		    'value' => $buyFormat,
		    'paramName' => '',
		    'paramValue' => ''),
		    array(
		    'name' => 'ReturnsAcceptedOnly',
		    'value' => $seller,
		    'paramName' => '',
		    'paramValue' => ''),
		    array(
		    'name' => 'FreeShippingOnly',
		    'value' => $freeship,
		    'paramName' => '',
		    'paramValue' => ''),
		    array(
		    'name' => 'ExpeditedShippingType',
		    'value' => $expship,
		    'paramName' => '',
		    'paramValue' => ''),
		     array(
		    'name' => 'MaxHandlingTime',
		    'value' => $days,
		    'paramName' => '',
		    'paramValue' => ''),
		  );

		  function buildURLArray ($filterarray) {

		  global $urlfilter;
		  global $i;
		  // Iterate through each filter in the array
		  foreach($filterarray as $itemfilter) {
		    // Iterate through each key in the filter
		     $flag="";
		    foreach($itemfilter as $filter => $filtervalue)
		    {
		    	if($filter=="value" && ($filtervalue=="" || $filtervalue=="-123"))
		    	{
		    		$flag="set";
		    	}


		    }
		    foreach ($itemfilter as $key =>$value) {
		      
		      	if($flag!="set")
		      	{

			      if(is_array($value)) {
			        foreach($value as $j => $content) { // Index the key for each value
			          $urlfilter .= "&itemFilter($i).$key($j)=$content";
			        }
			      }
			      else {
			        if($value != "") {
			          $urlfilter .= "&itemFilter($i).$key=$value";
			        }

			      }
			      //$ind++;
			  	}
			  	
		    }
		    if($flag=="set")
			{
					$i--;
			}
		    $i++;
		  }
		  return "$urlfilter";
		} // End of buildURLArray function

		// Build the indexed item filter URL snippet
		


		// Construct the findItemsByKeywords HTTP GET call
		$apicall = "$endpoint?";
		$apicall .= "OPERATION-NAME=findItemsByKeywords";
		$apicall .= "&SERVICE-VERSION=$version";
		$apicall .= "&SECURITY-APPNAME=$appid";
		$apicall .= "&GLOBAL-ID=$globalid";
		$apicall .= "&keywords=$safequery";
		$apicall .= "&paginationInput.entriesPerPage=5";
		$apicall .= "&paginationInput.pageNumber=1";
		$apicall .= "&RESPONSE-DATA-FORMAT=$responseEncoding";
		$apicall .= "&outputSelector[0]=SellerInfo";
		$apicall .= "&outputSelector[1]=PictureURLSuperSize";
		$apicall .= "&outputSelector[2]=StoreInfo";
		$apicall .= "&sortOrder=$sortingOrder";
		$apicall .= buildURLArray($filterarray);


		$resp = simplexml_load_file($apicall);
		// echo "<p> <a href=\"$apicall\">".$apicall."</a></p>";

		if(($resp->paginationOutput->totalEntries[0])==0)
		{
			$jdata=array(
				'ack'=>'No Results Found',
				);


		}
		else
		{

			$jdata= array(
				'ack'=>(string)$resp->ack,
				'resultCount'=>(string)$resp->paginationOutput->totalEntries[0],
				'pageNumber'=>(string)$resp->paginationOutput->pageNumber[0],
				'itemCount'=>(string)$resp->paginationOutput->entriesPerPage[0],
				);


			$i=0;
			foreach ($resp->searchResult->item as $item) {


				$jdata["item$i"]=array(

					'basicInfo'=>array(
						'title'=>isset($item->title) ? (string)$item->title : "N/A",
						'viewItemURL'=>isset($item->viewItemURL) ? (string)$item->viewItemURL : "N/A",
						'galleryURL'=>isset($item->galleryURL) ? (string)$item->galleryURL : "N/A",
						'pictureURLSuperSize'=>isset($item->pictureURLSuperSize) ? (string)$item->pictureURLSuperSize : "N/A",
						'convertedCurrentPrice'=>isset($item->sellingStatus[0]->convertedCurrentPrice) ? (string)$item->sellingStatus[0]->convertedCurrentPrice : "N/A",
						'shippingServiceCost'=>isset($item->shippingInfo[0]->shippingServiceCost) ?  (string)$item->shippingInfo[0]->shippingServiceCost: "N/A", 
						'conditionDisplayName'=>isset($item->condition[0]->conditionDisplayName) ?  (string)$item->condition[0]->conditionDisplayName: "N/A",
						'listingType'=>isset($item->listingInfo[0]->listingType) ? (string)$item->listingInfo[0]->listingType : "N/A",
						'location'=>isset($item->location[0]) ?  (string)$item->location[0]: "N/A",
						'categoryName'=>isset($item->primaryCategory[0]->categoryName) ? (string)$item->primaryCategory[0]->categoryName : "N/A",
						'topRatedListing'=>isset($item->topRatedListing[0]) ?  (string)$item->topRatedListing[0]: "N/A",
						),
					'sellerInfo'=>array(
						'sellerUserName'=>isset($item->sellerInfo[0]->sellerUserName) ? (string)$item->sellerInfo[0]->sellerUserName : "N/A",
						'feedbackScore'=>isset($item->sellerInfo[0]->feedbackScore) ? (string)$item->sellerInfo[0]->feedbackScore : "N/A",
						'positiveFeedbackPercent'=>isset($item->sellerInfo[0]->positiveFeedbackPercent) ?  (string)$item->sellerInfo[0]->positiveFeedbackPercent: "N/A",
						'feedbackRatingStar'=>isset($item->sellerInfo[0]->feedbackRatingStar) ?  (string)$item->sellerInfo[0]->feedbackRatingStar: "N/A",
						'topRatedSeller'=>isset($item->sellerInfo[0]->topRatedSeller) ? (string)$item->sellerInfo[0]->topRatedSeller : "N/A",
						'sellerStoreName'=>isset($item->storeInfo[0]->storeName) ? (string)$item->storeInfo[0]->storeName : "N/A",
						'sellerStoreURL'=>isset($item->storeInfo[0]->storeURL) ? (string)$item->storeInfo[0]->storeURL : "N/A",
						),
					'shippingInfo'=>array(
						'shippingType'=>isset($item->shippingInfo[0]->shippingType) ? (string)$item->shippingInfo[0]->shippingType : "N/A",
						'shipToLocations'=>isset($item->shippingInfo[0]->shipToLocations) ? (string)$item->shippingInfo[0]->shipToLocations : "N/A",
						'expeditedShipping'=>isset($item->shippingInfo[0]->expeditedShipping) ?  (string)$item->shippingInfo[0]->expeditedShipping: "N/A",
						'oneDayShippingAvailable'=>isset($item->shippingInfo[0]->oneDayShippingAvailable) ? (string)$item->shippingInfo[0]->oneDayShippingAvailable : "N/A",
						'returnsAccepted'=>isset($item->returnsAccepted[0]) ?  (string)$item->returnsAccepted[0]: "N/A",
						'handlingTime'=>isset($item->shippingInfo[0]->handlingTime) ? (string)$item->shippingInfo[0]->handlingTime : "N/A",
						)

					);
				
				$i++;

			}
		}


		
		$returnjson=json_encode($jdata,JSON_PRETTY_PRINT | JSON_UNESCAPED_SLASHES);
		// $returnjson=json_encode($returnj);
		
		header('Content-Type: application/json');
		echo $returnjson;



?>
 
