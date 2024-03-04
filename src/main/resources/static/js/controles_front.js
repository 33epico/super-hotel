
	
	function activaCarrousel(){
		$("#0").removeClass('carousel-item').addClass(
		'carousel-item active');
	}	

	function mapaGaleria(x,y){
	 var map = new google.maps.Map(document.getElementById('map'), {
		   zoom: 20,
		   center: new google.maps.LatLng(x, y),
		   mapTypeId: google.maps.MapTypeId.ROADMAP
	 });

	 
	 var marker = new google.maps.Marker({
	     position: new google.maps.LatLng(x, y),
	     map: map,
	   });
	   
	}

