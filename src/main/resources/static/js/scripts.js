$(document).ready(function() {
  // $('[data-toggle=offcanvas]').click(function() {
  //   $('.row-offcanvas').toggleClass('active');
  // });

  //start of carousel script
 //  	$('#myCarousel').carousel({
	// interval: 10000
	// })
    
 //    $('#myCarousel').on('slid.bs.carousel', function(e) {
    	// alert("slid"+e.currentTarget.id);
//	});

  //end of carousel script

//  STUDENT_METHOD.loadStudentData();

    $('#autocomplete-ajax').autocomplete({
    serviceUrl: "http://localhost/SocialGraph/data/autocom.json",
    onSelect: function (suggestion) {
        alert('You selected: ' + suggestion.value + ', ' + suggestion.data);
    }
	});

	//First graph
 //     alchemy.begin({
 //        dataSource: "data/charlize.json",
 //        nodeCaption: 'name',
 //        nodeMouseOver: 'name',
 //        cluster: true,
 //        clusterColours: ["#1B9E77","#D95F02","#7570B3","#E7298A","#66A61E","#E6AB02"]});
	// });

  
       $.ajax({
           type: "POST",
            url: "http://10.10.101.76:8080/submitQuery",
            beforeSend: function(xhr){xhr.setRequestHeader('content-type', 'application/json');},
            data:"MATCH (n) WHERE has(n.name) RETURN n.name AS name",
            success: function(result) {
              console.log(result);
             },
             error:function(result){
             console.log(result);
             }
          });

	//second graph
	var config = {
      dataSource: 'data/charlize.json',
      forceLocked: false,
      linkDistance: function(){ return 40; },

      nodeTypes: {"type":["movie", 
                          "award", 
                          "person"]},
      caption: function(node){ 
          return node.caption; 
      }
    };

    alchemy = new Alchemy(config);


// var STUDENT_METHOD ={

//         handlerData:function(resJSON){
        	
//             var templateSource   = $("#student-template").html(),

//                 template = Handlebars.compile(templateSource),

//                 studentHTML = template(resJSON);

//            $('#my-container').html(studentHTML);
//            // console.log($("#student-template"))
//         },
//         loadStudentData : function(){
//         	$(".ajax-loader").removeClass("hide");
//         	setTimeout(function(){
//         		  $.ajax({
//                 url:"http://localhost/SocialGraph/data/studentData.json",
//                 method:'get',
//                 success:function(resJSON){
//                 	$(".ajax-loader").addClass("hide");
//                 	STUDENT_METHOD.handlerData(resJSON);
//                 }
//             	})
//         	},1000);
//         }
});
