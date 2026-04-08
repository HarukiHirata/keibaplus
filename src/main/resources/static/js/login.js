document.addEventListener("DOMContentLoaded", function() {
    const registeredMessage = document.getElementById("registered");
    if (registeredMessage) {
        setTimeout(() => {
            const bsAlert = bootstrap.Alert.getOrCreateInstance(registeredMessage);
            bsAlert.close();
        }, 3000);
    }

    const errorMessage = document.getElementById("error");
    if (errorMessage) {
        setTimeout(() => {
            const bsAlert = bootstrap.Alert.getOrCreateInstance(errorMessage);
            bsAlert.close();
        }, 3000);
    }

    const logoutMessage = document.getElementById("logout");
    if (logoutMessage) {
        setTimeout(() => {
            const bsAlert = bootstrap.Alert.getOrCreateInstance(logoutMessage);
            bsAlert.close();
        }, 3000);
    }

});