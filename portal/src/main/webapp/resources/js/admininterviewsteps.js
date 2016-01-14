/**
 * This method is used to show interview Step on interview booking by ccr or admin 
 * going next step and back step
 */

var myclick=0,nextstep=function(){
myclick<3&&(myclick+=1,$("#step"+myclick).css("border-top","6px solid #F36F21"),$("#step0"+myclick).attr("src","resources/images/step0"+myclick+".png"),$("#step0"+(myclick+1)).attr("src","resources/images/step0"+(myclick+1)+".png"),myclick>1&&myclick<=3&&$("#step0"+myclick).attr("src","resources/images/step00"+myclick+".png"))},backstep=function(){myclick>0&&($("#step"+myclick).css("border-top","6px solid #DDD"),$("#step0"+(myclick+1)).attr("src","resources/images/step"+(myclick+1)+".png"),$("#step0"+myclick).attr("src","resources/images/step0"+myclick+".png"),1==myclick&&$("#step0"+myclick).attr("src","resources/images/step"+myclick+".png"),myclick-=1)},currentstep=function(a){myclick=a-1;for(var b=3;b>=a;b--)$("#step"+b).css("border-top","6px solid #DDD"),$("#step0"+(b+1)).attr("src","resources/images/step"+(b+1)+".png"),$("#step0"+b).attr("src","resources/images/step0"+b+".png"),1==b&&$("#step0"+b).attr("src","resources/images/step"+b+".png")};