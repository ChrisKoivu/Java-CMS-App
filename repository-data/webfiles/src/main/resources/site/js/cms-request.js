
 

console.log("cms request js loaded");

document.addEventListener('load', (event) => {
    var containerNodes = document.querySelectorAll("div.hst-container-item.hippo-overlay-box");
    
    console.log("container nodes: ", containerNodes);
    for (i = 0; i < containerNodes.length; i++) {
        console.log(containerNodes[i]);
    
    }


  });
  document.addEventListener('DOMContentLoaded',
    function() {
        console.log("dom loaded");
     
        
    }
  
  );
   
  var elem = document.querySelector("body > div.container > div.row.top-3x > div > div > ul > li.active > a")

  elem.addEventListener('click', event => {
    //handle click

    console.log( "container item clicked");
    event.preventDefault();
  });