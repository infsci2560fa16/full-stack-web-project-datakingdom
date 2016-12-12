function operation(step, type) {
	var kiwiElement = document.getElementById(type + "-value");

	if(parseInt(kiwiElement.value) + step < 0) {
		alert("cannot be negative!!");
		return;
	}

	kiwiElement.value = parseInt(kiwiElement.value) + step;
	document.getElementById("summary-" + type).value = kiwiElement.value;
	sum();
}

function sum() {
	var kiwiTotal = parseInt(document.getElementById("kiwi-value").value) *
			parseFloat(document.getElementById("kiwi-price").value);
	var mangoTotal = parseInt(document.getElementById("mango-value").value) *
			parseFloat(document.getElementById("mango-price").value);
	document.getElementById("summary-total").value = kiwiTotal + mangoTotal;
}

function submitForm() {
    
        if(confirm('Are you sure to purchase?')) {
            var url = "writedb.jsp?kiwi=" + document.getElementById("kiwi-value").value
                + "&mango=" + document.getElementById("mango-value").value
                + "&total=" + document.getElementById("summary-total").value;
            // Sends an xml http request
            var xmlhttp = GetXmlHttpObject();
            if (xmlhttp == null){
                alert("Your browser does not support Ajax HTTP");
                return;
            }
            xmlhttp.onreadystatechange = function(){
                if(xmlhttp.readyState === XMLHttpRequest.DONE && xmlhttp.status === 200) {
                    document.getElementById("mango-value").value = "0";                
                    document.getElementById("kiwi-value").value = "0";
                    document.getElementById("summary-total").value = "0";
                    document.getElementById("summary-kiwi").value = "0";
                    document.getElementById("summary-mango").value = "0";
                }
            };
            xmlhttp.open("GET", url, true);
            xmlhttp.send();
        }
}

function GetXmlHttpObject() {
	if (window.XMLHttpRequest) {
	   return new XMLHttpRequest();
	}
	if (window.ActiveXObject) {
	  return new ActiveXObject("Microsoft.XMLHTTP");
	}
	return null;
}