function count(f,n,u)
{
  f.line_sum[n].value = f.line[n].value * u;
  f.line_sum[n].value = Math.ceil(f.line_sum[n].value * 1000) /1000;
  f.line_sum[n].value = Math.floor(f.line_sum[n].value * 1000) /1000;
  f.line_sum[n].value = Math.round(f.line_sum[n].value * 100)/100;
  if(f.line_sum[n].value == "NaN")
  {
    alert("Error:\nYou may only enter numbers...\nPlease retry");
    f.line[n].value = f.line[n].value.substring(0, f.line[n].value.length-1);
    f.line_sum[n].value = f.line[n].value * u;
    if(f.line_sum[n].value == "0") f.line_sum[n].value = "";
  }
  else 
  {
    var gt = 0;
    for(i=0; i < f.line_sum.length; i++)
    {
      gt += Math.ceil(f.line_sum[i].value * 1000) /1000;
    }
    gt = Math.round(gt * 1000)/1000;
    f.grand_total.value = "" + gt;
    decimal(f);
   }
}

function get_data(f)
{
//  var order_data = "This Order is ...\n";
//  for(i=0; i < f.line.length; i++)
//  {
//    if(f.line[i].value == "") f.line[i].value = "0";
//    order_data += "Line " +i+ " = "+f.line[i].value +" Qty\n";
//  }
//  if(f.grand_total.value == "") f.grand_total.value = "Nil";
//  order_data += "Total Order Value = " + f.grand_total.value;
//  document.g.order.value = order_data; 
	var order_data ={};
	order_data.kiwi = f.line[0].value === 0 ? 0 : f.line[0].value;
	order_data.mango  = f.line[1].value === 0 ? 0 : f.line[1].value;
	order_data.cantaloupe = f.line[2].value === 0 ? 0 : f.line[2].value;
	order_data.total = f.grand_total.value;
	var jsonString= JSON.stringify(order_data);
	document.g.order.value = jsonString;
}

function decimal(f)
{
	var d = "";
  for(i=0; i<f.line_sum.length; i++)
  {
    d = f.line_sum[i].value.indexOf(".");
    if(d == -1 && f.line[i].value !== 0) f.line_sum[i].value += ".00";
    if(d == (f.line_sum[i].value.length-2)) f.line_sum[i].value += "0";
    if(f.line_sum[i].value == "00") f.line_sum[i].value="";
  }
  d = f.grand_total.value.indexOf(".");
  if(d == -1) f.grand_total.value += ".00";
  if(d == (f.grand_total.value.length-2)) f.grand_total.value += "0";
}

function send_data(g)
{
  get_data(document.f);
  if(document.f.grand_total.value == "Nil")
  {
    var conf = confirm("No items are entered - \nDo you want to submit a blank order?");
    if(conf)g.submit(); else init();
  }
  else g.submit();
}

function init()
{
  document.f.reset();
  document.f.line[0].select();
  document.f.line[0].focus();
  document.g.order.value="";
}

window.onload=init;

