		console.log("this is script file")
		
	/*	
		function hide(){

					document.getElementById('content').style.height="0px"
					document.getElementById('content').style.display="none"
					document.getElementById('show').style.display="inline"
					
				}
		
		function show(){

			document.getElementById('content').style.height="400px"
			document.getElementById('content').style.display="block"
			document.getElementById('show').style.display="none"

		}*/
	
		/*
		function toggleSidebar() {
			const sidebar = document.querySelector('.sidebar');
			const content = document.querySelector('.sidebar-content');
			const crossBtn = document.getElementById('hide');
			const showBtn = document.getElementById('show');
		
			// Toggle visibility classes
			sidebar.classList.toggle('hidden');
			content.classList.toggle('expanded');
		
			// Show or hide buttons based on the state
			if (sidebar.classList.contains('hidden')) {
				showBtn.style.display = 'inline-block';
				crossBtn.style.display = 'none';
			} else {
				showBtn.style.display = 'none';
				crossBtn.style.display = 'inline-block';
			}
		}
		
		// Event listeners for buttons
		document.getElementById('hide').addEventListener('click', toggleSidebar);
		document.getElementById('show').addEventListener('click', toggleSidebar);
			
		*/
			
		function toggleSidebar() {
			const sidebar = document.querySelector('.sidebar');
			const content = document.querySelector('.sidebar-content');
			const toggleBtn = document.getElementById('toggleBtn');
		
			// Toggle the sidebar's visibility
			sidebar.classList.toggle('hidden');
			content.classList.toggle('expanded');
		
			// Change the icon dynamically
			if (sidebar.classList.contains('hidden')) {
				toggleBtn.classList.remove('fa-times');
				toggleBtn.classList.add('fa-bars');
			} else {
				toggleBtn.classList.remove('fa-bars');
				toggleBtn.classList.add('fa-times');
			}
		}
		
		function toggleSidebar() {
		    document.getElementById("sidebar").classList.toggle("hide");
		}

			
