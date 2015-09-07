 // This is called with the results from from FB.getLoginStatus().
  function statusChangeCallback(response) {
    console.log('statusChangeCallback');
    console.log(response);
    // The response object is returned with a status field that lets the
    // app know the current login status of the person.
    // Full docs on the response object can be found in the documentation
    // for FB.getLoginStatus().
    if (response.status === 'connected') {
      // Logged into your app and Facebook.
      testAPI();
    } else if (response.status === 'not_authorized') {
      // The person is logged into Facebook, but not your app.
      document.getElementById('status').innerHTML = 'Please log ' +
        'into this app.';
    } else {
      // The person is not logged into Facebook, so we're not sure if
      // they are logged into this app or not.
      document.getElementById('status').innerHTML = 'Please log ' +
        'into Facebook.';
    }
  }

  // This function is called when someone finishes with the Login
  // Button.  See the onlogin handler attached to it in the sample
  // code below.
  function checkLoginState() {
    FB.getLoginStatus(function(response) {
      statusChangeCallback(response);
    });
  }

  window.fbAsyncInit = function() {
  FB.init({
    appId      : '507028316120834',
    cookie     : true,  // enable cookies to allow the server to access 
                        // the session
    xfbml      : true,  // parse social plugins on this page
    version    : 'v2.2' // use version 2.2
  });

  // Now that we've initialized the JavaScript SDK, we call 
  // FB.getLoginStatus().  This function gets the state of the
  // person visiting this page and can return one of three states to
  // the callback you provide.  They can be:
  //
  // 1. Logged into your app ('connected')
  // 2. Logged into Facebook, but not your app ('not_authorized')
  // 3. Not logged into Facebook and can't tell if they are logged into
  //    your app or not.
  //
  // These three cases are handled in the callback function.

  FB.getLoginStatus(function(response) {
    statusChangeCallback(response);
  });

  };

  // Load the SDK asynchronously
  (function(d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s); js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
  }(document, 'script', 'facebook-jssdk'));

  // Here we run a very simple test of the Graph API after login is
  // successful.  See statusChangeCallback() for when this call is made.
  function testAPI() {
	  res = null;
    console.log('Welcome!  Fetching your information.... ');
    FB.api('/me', function(response) {
      console.log('Successful login for: ' + response.name);
      document.getElementById('status').innerHTML =
        'Thanks for logging in, ' + response.name + '!';
    });
    
//    FB.api("/me/friends", function (response) {
//    		res = response;
//    	 console.log(response);
//    	      if (response && !response.error) {
//    	        /* handle the result */
//    	    	  $.ajax({
//    	    		url:"data/fb1.json",
//    	    		beforeSend: function(xhr){xhr.setRequestHeader('content-type', 'application/json');},
//    	    		success:function(result){
//    	    			console.log(result);
//    	    			
//    	    			var margin = 20,
//    	    		    diameter = 1000;
//
//    	    		var color = d3.scale.linear()
//    	    		    .domain([-1, 5])
//    	    		    .range(["hsl(152,80%,80%)", "hsl(228,30%,40%)"])
//    	    		    .interpolate(d3.interpolateHcl);
//
//    	    		var pack = d3.layout.pack()
//    	    		    .padding(2)
//    	    		    .size([diameter - margin, diameter - margin])
//    	    		    .value(function(d) { return d.size; })
//
//    	    		var svg = d3.select("#graph").append("svg")
//    	    		    .attr("width", diameter)
//    	    		    .attr("height", diameter)
//    	    		  .append("g")
//    	    		    .attr("transform", "translate(" + diameter / 2 + "," + diameter / 2 + ")");
//
//    	    		//d3.json(JSON.stringify(result), function(error, root) {
//    	    		 var root = JSON.parse(result);
//    	    		//if (error) throw error;
//
//    	    		  var focus = root,
//    	    		      nodes = pack.nodes(root),
//    	    		      view;
//
//    	    		  var circle = svg.selectAll("circle")
//    	    		      .data(nodes)
//    	    		    .enter().append("circle")
//    	    		      .attr("class", function(d) { return d.parent ? d.children ? "node" : "node node--leaf" : "node node--root"; })
//    	    		      .style("fill", function(d) { return d.children ? color(d.depth) : null; })
//    	    		      .attr("data-node",function(d){return d.name;})
//    	    		      .on("click", function(d) {
//    	    		    	  if(d.value == 2345){goToDetails(d);}
//    	    		    	  if (focus !== d)  zoom(d), d3.event.stopPropagation(); 
//    	    		    });
//
//    	    		  var text = svg.selectAll("text")
//    	    		      .data(nodes)
//    	    		    .enter().append("text")
//    	    		      .attr("class", "label")
//    	    		      .style("fill-opacity", function(d) { return d.parent === root ? 1 : 0; })
//    	    		      .style("display", function(d) { return d.parent === root ? null : "none"; })
//    	    		      .text(function(d) { return d.name; });
//
//    	    		  var node = svg.selectAll("circle,text");
//
//    	    		  d3.select("body")
//    	    		      .style("background", color(-1))
//    	    		      .on("click", function() { zoom(root); });
//
//    	    		  zoomTo([root.x, root.y, root.r * 2 + margin]);
//
//    	    		  function zoom(d) {
//    	    		    var focus0 = focus; focus = d;
//
//    	    		    var transition = d3.transition()
//    	    		        .duration(d3.event.altKey ? 7500 : 750)
//    	    		        .tween("zoom", function(d) {
//    	    		          var i = d3.interpolateZoom(view, [focus.x, focus.y, focus.r * 2 + margin]);
//    	    		          return function(t) { zoomTo(i(t)); };
//    	    		        });
//
//    	    		    transition.selectAll("text")
//    	    		      .filter(function(d) { return d.parent === focus || this.style.display === "inline"; })
//    	    		        .style("fill-opacity", function(d) { return d.parent === focus ? 1 : 0; })
//    	    		        .each("start", function(d) { if (d.parent === focus) this.style.display = "inline"; })
//    	    		        .each("end", function(d) { if (d.parent !== focus) this.style.display = "none"; });
//    	    		  }
//
//    	    		  function zoomTo(v) {
//    	    		    var k = diameter / v[2]; view = v;
//    	    		    node.attr("transform", function(d) { return "translate(" + (d.x - v[0]) * k + "," + (d.y - v[1]) * k + ")"; });
//    	    		    circle.attr("r", function(d) { return d.r * k; });
//    	    		  }
//    	    		  
//    	    		  function goToDetails(d){
//    	    			
//    	    				  alert(d.name);
//    	    			     
//    	    		  }
//    	    		  
//    	    		//});
//
//    	    		d3.select(self.frameElement).style("height", diameter + "px");
//    	    		}
//    	    	  });
//    	    	  
//    	    	
//    	      }
//    	    }
//    	);
  }