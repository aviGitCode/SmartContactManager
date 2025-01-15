/*
    function confirmDelete(event, element) {
        event.preventDefault(); // Prevent the default link action

        Swal.fire({
            title: 'Are you sure?',
            text: "This action cannot be undone!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                // Redirect to the delete URL
                window.location.href = element.getAttribute('href');
            }
        });
    }

    // Display success or error messages from the session
    document.addEventListener("DOMContentLoaded", function () {
        const message = [[${session.message}]];
        if (message) {
            Swal.fire({
                title: message.content,
                icon: message.type === 'success' ? 'success' : 'error',
                timer: 3000,
                showConfirmButton: false
            });
        }
    });

*/