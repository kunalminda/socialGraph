/* activate scrollspy menu */
$('body').scrollspy({
  target: '#navbar-collapsible',
  offset: 52
});

/* smooth scrolling sections */
$('a[href*=#]:not([href=#])').click(function() {
    if (location.pathname.replace(/^\//,'') == this.pathname.replace(/^\//,'') && location.hostname == this.hostname) {
      var target = $(this.hash);
      target = target.length ? target : $('[name=' + this.hash.slice(1) +']');
      if (target.length) {
        $('html,body').animate({
          scrollTop: target.offset().top - 50
        }, 800);
        
        if (this.hash=="#section1") {
            $('.scroll-up').hide();
        }
        else {
            $('.scroll-up').show();
        }
        
        
        // activte animations in this section
        target.find('.animate').delay(1200).addClass("animated");
        setTimeout(function(){
            target.find('.animated').removeClass("animated");
        },2000);
        
        return false;
      }
    }
});

$(document).ready(function(){
	
	$("#btnContactUs").on("click",function(){
		//alert("contact");
		var email = $("#email").val();
		 $.ajax({
	    		url:"/send/email/"+email+"/",
	    		beforeSend: function(xhr){xhr.setRequestHeader('content-type', 'application/json');},
	    		success:function(result){
	    			console.log(result);
	    			$(".label-contactus").text("your email has been sent successfully!");
	    		}
		 });
	});
	
	$(".scroll-down").on("click",function(){
		var data = {"product":$("#inputProduct").val(),"cat":$("#inputCat").val(),"subCat":$("#inputSubCat").val()};
		console.log(data); 
		$.ajax({
	    		url:"/searchSeller",
	    		type:"POST",
	    		data:JSON.stringify(data),
	    		beforeSend: function(xhr){xhr.setRequestHeader('content-type', 'application/json');},
	    		success:function(result){
	    			console.log("------------------------result");
	    			console.log(result);
	    			renderGraph(result);
	    		}
		 });
		
		if($("#inputCat").val() == "Apparels"){
			var data = ["Apoorva Jain", "Sells women clothing and has a surplus of dresses","Gaurav Gupta","sells men clothing and has a surplus of designer shirts","P.Venkataraman","sells children apparels and has a surplus on kids wear","Geeta Goswami","sells children apparels and has a surplus on kids wear"];
		}
		if($("#inputProduct").val() == "Nike"){
			var data = ["Nikita Arora","Huge Surplus of Nike floaters and Women Shoes","Suraj Nigam","Medium level surplus of Nike Women Shoes","Ram Wagley","Huge Surplus of Nike Men shoes and Nike Floaters","Harshika Yadav","Huge Surplus of Nike Men Shoes and Nike Floaters"];
		}
		if($("#inputSubCat").val() == "Tablet"){
			var data = ["Chintamani Joshi","Huge Surplus of tablets","Saqib Kamal","Huge Surplus of tablets","Peter Mathews","Huge Surplus of tablets","Ruchika Nikki","Huge Surplus of Tablet"];
		}
		
		$($(".panel-heading h3")[0]).text(data[0]);
		$($(".panel-heading h3")[1]).text(data[2]);
		$($(".panel-heading h3")[2]).text(data[4]);
		$($(".panel-heading h3")[3]).text(data[6]);
		
		$($(".panel-body p")[0]).text(data[1]);
		$($(".panel-body p")[1]).text(data[3]);
		$($(".panel-body p")[2]).text(data[5]);
		$($(".panel-body p")[3]).text(data[7]);
	});
	
	$("#btnMakeContact").on("click",function(){
		 $('html, body').animate({
		        scrollTop: $(".make-contact").offset().top - 30
		    }, 2000);
		 
		 $("#firstName").val($(".text-name").text());
		 $("#email").val($(".text-email").text());
		 $("#textarea").val("I would like to contact you for selling bulk order through Snapdeal. Please contact me for more details.");
	});
	
	
	function renderGraph(result){
		var margin = 20,
	    diameter = 700;

	var color = d3.scale.linear()
	    .domain([-1, 5])
	    .range(["hsl(152,80%,80%)", "hsl(228,30%,40%)"])
	    .interpolate(d3.interpolateHcl);

	var pack = d3.layout.pack()
	    .padding(2)
	    .size([diameter - margin, diameter - margin])
	    .value(function(d) { return d.size; })

	var svg = d3.select("#graph").append("svg")
	    .attr("width", diameter)
	    .attr("height", diameter)
	  .append("g")
	    .attr("transform", "translate(" + diameter / 2 + "," + diameter / 2 + ")");

	var width = 800, height = 800;
	// force layout setup
	var force = d3.layout.force()
	        .charge(-200).linkDistance(30).size([width, height]);

	// setup svg div
/*	var svg = d3.select("#graph").append("svg")
	        .attr("width", "100%").attr("height", "100%")
	        .attr("pointer-events", "all");*/

//	d3.json(JSON.stringify(result), function(error, root) {
	 var root = result;
	//if (error) throw error;
	 $(".noSup").text(parseTree(result));
	 resultObj = result;
	  var focus = root,
	      nodes = pack.nodes(root),
	      view;

	  var circle = svg.selectAll("circle")
	      .data(nodes)
	    .enter().append("circle")
	      .attr("class", function(d) { return d.parent ? d.children ? "node" : "node node--leaf" : "node node--root"; })
	      .style("fill", function(d) { return d.children ? color(d.depth) : null; })
	      .attr("data-node",function(d){return d.name;})
	      .on("click", function(d) {
	    	  if(d.value == 2345){goToDetails(d);}
	    	  if (focus !== d)  zoom(d), d3.event.stopPropagation(); 
	    });

	  var text = svg.selectAll("text")
	      .data(nodes)
	    .enter().append("text")
	      .attr("class", "label")
	      .style("fill-opacity", function(d) { return d.parent === root ? 1 : 0; })
	      .style("display", function(d) { return d.parent === root ? null : "none"; })
	      .text(function(d) { return d.name; });

	  var node = svg.selectAll("circle,text");

	  d3.select("body")
	      .style("background", color(-1))
	      .on("click", function() { zoom(root); });

	  zoomTo([root.x, root.y, root.r * 2 + margin]);

	  function zoom(d) {
	    var focus0 = focus; focus = d;

	    var transition = d3.transition()
	        .duration(d3.event.altKey ? 7500 : 750)
	        .tween("zoom", function(d) {
	          var i = d3.interpolateZoom(view, [focus.x, focus.y, focus.r * 2 + margin]);
	          return function(t) { zoomTo(i(t)); };
	        });

	    transition.selectAll("text")
	      .filter(function(d) { return d.parent === focus || this.style.display === "inline"; })
	        .style("fill-opacity", function(d) { return d.parent === focus ? 1 : 0; })
	        .each("start", function(d) { if (d.parent === focus) this.style.display = "inline"; })
	        .each("end", function(d) { if (d.parent !== focus) this.style.display = "none"; });
	  }

	  function zoomTo(v) {
	    var k = diameter / v[2]; view = v;
	    node.attr("transform", function(d) { return "translate(" + (d.x - v[0]) * k + "," + (d.y - v[1]) * k + ")"; });
	    circle.attr("r", function(d) { return d.r * k; });
	  }
	  
	  function goToDetails(d){
		  var data = {
				  "query":"MATCH (sellers:SELLER{name:'"+d.name+"'}) return sellers.school, sellers.state, sellers.college, sellers.name"
		  };

		  $.ajax({
	    		url:"/submitQuery",
	    		type:"POST",
	    		data:JSON.stringify(data),
	    		beforeSend: function(xhr){xhr.setRequestHeader('content-type', 'application/json');},
	    		success:function(result){
	    			var response = result.results[0].data[0].row;
	    			$(".text-place").text(response[1]);
	    			$(".text-college").text(response[0]);
	    			$(".text-school").text(response[2]);
	    			setTimeout(function(){
	    			$('html,body').animate({
	    		          scrollTop: $(".details-section").offset().top - 50
	    		     }, 1500);
	    			},1000);
	    		}
		 });
		  
		     $(".text-name").text(d.name);
		     
		     if(d.name == "Kapil Minda")
		    	 $(".text-email").text("kunal.minda@snapdeal.com");
		     else if(d.name == "Indrani Mukherjee")
		    	 $(".text-email").text("saloni.jain@snapdeal.com");
		     else if(d.name == "Vikas Gupta")
		    	 $(".text-email").text("vikas.kumar06@snapdeal.com");
		     else if(d.name == "Atul Sharma")
		    	 $(".text-email").text("atul.sharma@snapdeal.com");
		     else if(d.name == "Vishal Vaibhav")
		    	 $(".text-email").text("vishal.vaibhav@snapdeal.com");
		     
		     $(".text-since").text("On Snapdeal since 2.5 years");
		     $(".text-rating").text("Rating on Snapdeal : 4.15");
	  }
	  
	    function parseTree(parent) {
	        var hasNonLeafNodes = false;
	        var childCount = 0;
	        
	        for (var child in parent) {
	            if (typeof parent[child] === 'object') {
	                // Parse this sub-category:
	                childCount += parseTree(parent[child]);
	                // Set the hasNonLeafNodes flag (used below):
	                hasNonLeafNodes = true;
	            }
	        }
	        
	        if (hasNonLeafNodes) {
	            // Add 'num_children' element and return the recursive result:
	            parent.num_children = childCount;
	            return childCount;
	        } else {
	            // This is a leaf item, so return 1:
	            return 1;
	        }
	    }
	  
//	});

	d3.select(self.frameElement).style("height", diameter + "px");
		
	}
});






