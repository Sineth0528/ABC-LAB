//nav bar hover
document.addEventListener("DOMContentLoaded", function () {
    // make it as accordion for smaller screens
    if (window.innerWidth > 992) {

        document.querySelectorAll('.navbar .nav-item').forEach(function (everyitem) {

            everyitem.addEventListener('mouseover', function (e) {

                let el_link = this.querySelector('a[data-bs-toggle]');

                if (el_link != null) {
                    let nextEl = el_link.nextElementSibling;
                    el_link.classList.add('show');
                    nextEl.classList.add('show');
                }

            });
            everyitem.addEventListener('mouseleave', function (e) {
                let el_link = this.querySelector('a[data-bs-toggle]');

                if (el_link != null) {
                    let nextEl = el_link.nextElementSibling;
                    el_link.classList.remove('show');
                    nextEl.classList.remove('show');
                }


            })
        });

    }
    // end if innerWidth
});

document.getElementById("registerForm").addEventListener("submit", function(event) {
    // Prevent the form from submitting by default
    event.preventDefault();

    // Get form fields values
    var firstname = document.getElementById("firstname").value.trim();
    var lastname = document.getElementById("lastname").value.trim();
    var birthday = document.getElementById("birthday").value.trim();
    var phonenumber = document.getElementById("phonenumber").value.trim();
    var email = document.getElementById("email").value.trim();
    var gender = document.getElementById("gender").value.trim();
    var password = document.getElementById("password").value.trim();
    var reenterpass = document.getElementById("reenterpass").value.trim();

    // Validate each field
    if (firstname === "" || lastname === "" || birthday === "" || phonenumber === "" || email === "" || gender === "" || password === "" || reenterpass === "") {
        alert("Please fill in all fields.");
        return;
    }

    // Validate email
    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        alert("Please enter a valid email address.");
        return;
    }

    // Validate phone number
    var phoneRegex = /^0\d{9}$/;
    if (!phoneRegex.test(phonenumber)) {
        alert("Please enter a valid phone number (07XXXXXXXX).");
        return;
    }

    // Validate password match
    if (password !== reenterpass) {
        alert("Passwords do not match.");
        return;
    }

    // Validate password contains at least one uppercase letter
    var uppercaseRegex = /[A-Z]/;
    if (!uppercaseRegex.test(password)) {
        alert("Password must contain at least one uppercase letter.");
        return;
    }

    alert("Registration successful! We have Sent Your Login Details to Your E-mail");
    this.submit();
});


/*
document.getElementById('registerForm').addEventListener('submit', function (event) {
    var password = document.getElementById('password').value;
    var reenterpass = document.getElementById('reenterpass').value;
    var passwordError = document.getElementById('passwordError');

    if (password !== reenterpass) {
        passwordError.style.display = 'block';
        event.preventDefault();
    } else {
        passwordError.style.display = 'none';
    }
});*/


function Quit() {
var result = confirm("Want to Quit?");
if (result) {
  return true;
  }
    else {
  return false;
 }
}

/*
function getType(){
    var type = $("#type").val();
    $('#date').val("");
    $.ajax({
        url: "RequestAppointment?aptype=" + type, // Add equals sign after aptype
        type: "GET",
        success: function(response){
            $("#date").prop("min", response); // Assuming response is the value you want to set as min
            $('#Payment').val(price);
        },
        error: function(){
            alert("Error fetching data");
        }
    }); 
}*/

function clearinputs(){
	 $('#date').val('');
     $('#Payment').val('');
}

function getType() {
    var type = $("#type").val();
    var date = $('#date').val();
    console.log(date);
    $.ajax({
        url: "RequestAppointment?aptype=" + type + "&date=" + date,
        type: "GET",
        success: function(response) {
            var responseData = response.split(",");
            var maxdate = responseData[0]; 
            var price = responseData[1];
            
            if(maxdate == 0){
				alert("Please Select Another Day!");
				$('#date').val('');
				$('#Payment').val('');
			}else{
				$('#Payment').val(price); 
			}
                
        },
        error: function() {
            alert("Error fetching data");
        }
    }); 
}

function updatebtn(appointmentId, status, userId) {
    // Display a confirmation dialog
    if (confirm("Are you sure?")) {
        // User confirmed, proceed with AJAX request
        $.ajax({
            type: 'POST',
            url: 'AppointmentDetailsController',
            data: {
                id: appointmentId,
                status: status,
                userId: userId
            },
            success: function(response) {
                if (response == "1") {
                    alert("Your update was successful! Thank you!");
                    location.reload();
                } else {
                    alert("Something went wrong! Please check again.");
                }
            },
            error: function() {
                alert("Error fetching data");
            }
        });
    } else {
        // User cancelled, do nothing
        return false;
    }
}

/*
function DownloadPdf(pdfname){
console.log(pdfname)
            const pdfUrl = 'AppointmentPDF/' + pdfname + '.pdf';
            const link = document.createElement('a');
            link.href = pdfUrl;
            link.download = 'downloaded_file.pdf';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
}*/

function downloadPdf(appointmentId) {
    // Construct the URL to the servlet including the appointmentId
    var url = 'DownloadPdfController?appointmentId=' + appointmentId;
    
    // Change the browser location to the servlet URL to initiate the file download
    window.location.href = url;
}

            

        
        







