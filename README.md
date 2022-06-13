<h1>Polynomial-Calculator</h1>
<p>Web application which allows user to calculate polynomial value, polynomial first derivative and polynomial first derivative value. 
</p>
<h2>Table of contents</h2>
<ul>
  <li>
    <a href="#generalInfo">General info</a>
  </li>
    <li>
    <a href="#images">Images</a>
  </li>
    <li>
    <a href="#setup">Setup</a>
  </li>
  <li>
    <a href="#howToUse">How to use</a>
  </li>
  <li>
    <a href="#technologies">Technologies used</a>
  </li>
   <li>
    <a href="#environment">Programming environment</a>
  </li>
  <li>
    <a href="#status">Project status</a>
  </li>
  </ul>
<h2 id="generalInfo">General info</h2>
<p>The goal of this application was to focus on learning how to write as clean code as possible. Good programming practices were of utmost importance as well.
The second goal was to learn Java syntax and understand how it works.
</p>
<p>Speaking of the Polynomial Calculator itself, it's a web application which allows user to perform various calculations and store them in both servlet context 
and a database. Backend was written in Java and frontend in html and css. JPA was used to handle database creation, update and other operations.
It was chosen over JDBC because it allows to perform all operations on database using Java and Java only. No SQL is needed.
</p>
<h2 id="images">Images</h2>
   <img src="https://user-images.githubusercontent.com/56251920/154491508-82548965-7dae-4248-83c1-39bd550f61b7.png" alt="application main page">
<h2 id="setup">Setup</h2>
<ol>
    <li>clone the repository</li>
    <li>build the application war file</li>
    <li>run command docker-compose up</li>
    <li>Go to browser at localhost:8080/PolynomialCalculator</li>
</ol>

<h2 id="howToUse">How to use</h2>
<p>When web app is up and running, to perform calculations you need to type polynomial factor values in input field, for instance
  for given equation y(d) = ax^2 + bx + c, type: a b c d
</p>
  <h2 id="technologies">Technologies used</h2> 
 <ul>
  <li>
   Java
  </li>
  <li>
    JPA
  </li>
   <li>
   html
  </li>
   <li>
   css
  </li>
   <li>
   docker
  </li>
  </ul>
   <h2 id="environment">Programming environment</h2> 
    <ul>
  <li>
   IntelliJ
  </li>
  </ul>
    <h2 id="status">Project status</h2> 
    <p>Finished</p>
  
  
  
