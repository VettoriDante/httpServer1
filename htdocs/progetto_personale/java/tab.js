function apriSez(Sez) {
    var i;
    var x = document.getElementsByClassName("sezione");
    for (i = 0; i < x.length; i++) {
      x[i].style.display = "none";  
    }
    document.getElementById(Sez).style.display = "block";  
  }
  