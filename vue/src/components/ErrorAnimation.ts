export function animateErrorWarning(show:boolean, errorBox:any) {

    if (show) {
        if (errorBox != null) {
            errorBox.classList.toggle("opacity-0");
            errorBox.classList.toggle("opacity-100");
            errorBox.classList.toggle("-right-60");
            errorBox.classList.toggle("right-28");
        }
    } else {
        if (errorBox != null) {
        errorBox.classList.toggle("opacity-100");
        errorBox.classList.toggle("opacity-0");
        errorBox.classList.toggle("right-28");
        errorBox.classList.toggle("-right-60");
        }
    }
};
