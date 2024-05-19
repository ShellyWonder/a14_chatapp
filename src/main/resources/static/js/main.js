// main.js: Initializes the application and handles login status checks.

import { handleLoginStatus, attachAuthEventListeners,
         attachEventListeners} from './auth.js';
import { updateNavbarChannels, updateChannelSelection } from './uiUtil.js';
import { attachChannelEventListeners} from './channel.js';


// user login check and update
document.addEventListener("DOMContentLoaded", function () {
    const isLoggedIn = sessionStorage.getItem("userId") !== null;
    handleLoginStatus(isLoggedIn);
    attachEventListeners();

    // Attach event listeners specific to the login and registration forms if they exist
    if (document.querySelector("#loginForm") || document.querySelector("#registrationFormContent")) {
      attachAuthEventListeners();
    }

    // Attach event listeners specific to the channel page if it exists
    if (document.querySelector(".channel")) {
      attachChannelEventListeners();
    }
    // Update the navbar channels for all pages
    updateNavbarChannels();

    // Update channel selection only on the index page
    if (document.querySelector(".list-group")) {
        updateChannelSelection();
    }

});