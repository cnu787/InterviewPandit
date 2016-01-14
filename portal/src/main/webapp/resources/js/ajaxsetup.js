var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
  "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
];
/**
 * this method is used to convert Gmt date to local date
 * @param myDate
 * @returns {Date}
 */
function convertGmtTOLocal(myDate){	
	var myNewDate=new Date(myDate);									
	var dValue = Date.UTC(myNewDate.getFullYear(),myNewDate.getMonth(),myNewDate.getDate(),myNewDate.getHours(),myNewDate.getMinutes(),myNewDate.getSeconds());
	var d = new Date();
    var n = d.getTimezoneOffset();
    return new Date(dValue +n);    
}