const termsAndPolicyCheck = document.getElementById('termsAndPolicyCheck');
const registerButton = document.getElementById('registerButton');

termsAndPolicyCheck.addEventListener('change', function () {
registerButton.disabled = !termsAndPolicyCheck.checked;
});
