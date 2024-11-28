<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" >
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        .nav-link
        {
            margin-left: 0;
            margin-right: 40px;
            color: rgba(255, 255, 255, 0.589);
            font-weight: bold;
            
        }
        .navbar
        {
            background-color: rgba(8, 78, 99, 0.726);
    
            font-family: 'Times New Roman', Times, serif, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif; 
        }
        .navbar .navbar-brand
        {
            color: rgba(255, 255, 255, 0.589);
            margin-left: 20px;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg">
        <div class="container-fluid">
          <a class="navbar-brand" href="UserHome">Smart Contact Manager</a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav ms-auto" id="profilNavDesign">
              <a class="nav-link" href="UserHome">Home</a>
              <a class="nav-link" href="userProfile">${session_user.getName()}</a>
              <a class="nav-link" href="logout">Logout</a>
            </div>
          </div>
        </div>
      </nav>
</body>
</html>