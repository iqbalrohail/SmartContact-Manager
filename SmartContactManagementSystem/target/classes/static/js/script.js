function w3_open() {
  document.getElementById("main").style.marginLeft = "25%";
  document.getElementById("mySidebar").style.width = "25%";
  document.getElementById("mySidebar").style.display = "block";
  document.getElementById("openNav").style.display = 'none';
}
function w3_close() {
  document.getElementById("main").style.marginLeft = "0%";
  document.getElementById("mySidebar").style.display = "none";
  document.getElementById("openNav").style.display = "inline-block";
}

const search=()=>{

let query=$("#search_input").val();


if (query=='')
{
$(".search-result").hide();



}else{

console.log(query);

let url =`http://localhost:8888/search/${query}`;
fetch(url).then((response)=>{
return response.json();
}).then((data)=>{
console.log(data);

let text = `<div class ='list-group'>`;

data.forEach((contact)=>{

text+=`<a href='/user/contact-detail/${contact.contactId}' class='list-group-item list-group-item-action'>
${contact.contactName}
</a>`

});


text+=`</div>`;

$(".search-result").html(text);
$(".search-result").show();

});





}


}