var message;

let pageNo=0;
let json = [];

function init() {
    message = document.getElementById('message');
    fetchData();
}

function fetchData() {
  fetch("/api/urls?pageNo=" + pageNo)
      .then(res => res.json())
      .then(res => {
          console.log(res)
          json = res.data;
          populate();
       })
      .catch(e => {
            message.innerHTML = '<div class="alert alert-danger" role="alert">Facing issue while downloading table data.</div>';
            setTimeout(() => message.innerHTML="", 3000);
      });
}

function onPrev() {
    if(pageNo<=0) return
    pageNo--;
    fetchData();
}

function onNext() {
    if(json.length==0) return
    pageNo++;
    fetchData();
}

function populate() {
    if (json.length === 0) {
        if(pageNo>0) pageNo--;
        return;
    }
    let keys = Object.keys(json[0]);
    var bodyRows = json.reduce((acc,row) => acc += '<tr><td><a target="_blank"  href="' + row.shortUrl + '">'+ row.shortUrl + '</a></td><td>' +row.count+ '</td></tr>', '');
    document.getElementById('tableBody').innerHTML = bodyRows;
}

function onShortUrl(longUrl) {
     fetch('/api/create-short-url', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          redirect: 'follow',
          body: JSON.stringify({longUrl})
     })
     .then(res => res.json())
     .then(res => {
        console.log(res);
        if(res.status === "failed") message.innerHTML = '<div class="alert alert-danger" role="alert">' + res.errorMessage + '</div>';
        else message.innerHTML = '<div class="alert alert-success" role="alert">' + res.data + '</div>';
        setTimeout(() => message.innerHTML="", 10000);
     }).catch((err) => {
        message.innerHTML = '<div class="alert alert-danger" role="alert">Facing server issue</div>';
        setTimeout(() => message.innerHTML="", 3000);
      });
    return false;
}

document.addEventListener('DOMContentLoaded', init, false);
