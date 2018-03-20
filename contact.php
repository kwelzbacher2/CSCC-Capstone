<?php	include("templates/header.php") ?>
  <div class="contactFormPannelBackground">
    <div class="contactFormPannel">
      <div class = "pageTitle"><p>Contact</p></div>
      <form class="contactForm" id="contactForm" action="contact.php" method="post">
        <div class="contactFormInputs">
          <input type="text" id="fname" name="firstName" maxlength="32" placeholder="First Name">
          <input type="text" id="lname" name="lastName"maxlength="32" placeholder="Last Name">
        </div>
        <div class = "contactFormInputs">
          <input type="email" id="email" name="email" maxlength="64" placeholder="Email Address">
          <input type="text" id="phone" name="phone" maxlength="11" placeholder="Phone Number" >
        </div>
        <div class="contactFormInputs">
          <textarea id="message" cols="30" rows="5" name="message" placeholder="Optional message..." ></textarea>
        </div>
        <div class = "buttonContainer">
          <button type="submit" class = "submitButton"></button>
        </div>
      </form>
    </div>
  </div>
  <div id = "miami" class = "contentPannel contactPannel">
    <div class = "pageTitle"><p>Miami</p></div>
    <div class = "contactBoxWrapper">
      <div class = "contactBoxContainer">
        <div class = "contactBox">
          <div class = "apptName">Suncrest Grove</div>
          <div>5638 North River Dr</div>
          <div>Miami FL 33135</div>
          <div>(305) 363-6732</div>
        </div>
        <div class = "contactWhitespace"></div>
        <div class = "contactBox">
          <div class = "apptName">Hidden Palms</div>
          <div>1295 Green Ln</div>
          <div>Miami FL 33135</div>
          <div>(305) 547-8936</div>
        </div>
        <div class = "contactWhitespace"></div>
        <div class = "contactBox">
          <div class = "apptName">Bayside Heights</div>
          <div>4832 Palm Ln</div>
          <div>Miami FL 33132</div>
          <div>(305) 326-8156</div>
        </div>
      </div>
    </div>
  </div>
  <div class = "separator separatorNewyork"></div>
  <div id = "newyork" class="contentPannel contactPannel">
    <div class = "pageTitle"><p>New York</p></div>
    <div class = "contactBoxWrapper">
      <div class = "contactBoxContainer">
        <div class = "contactBox">
          <div class = "apptName">Highland Square</div>
          <div>3296 Lexington Ave</div>
          <div>New York, NY 10118</div>
          <div>(929) 538-3262</div>
        </div>
        <div class = "contactWhitespace"></div>
        <div class = "contactBox">
          <div class = "apptName">Hudson Court</div>
          <div>1249 Madison Ave</div>
          <div>New York, NY 10016</div>
          <div>(929) 235-7291</div>
        </div>
        <div class = "contactWhitespace"></div>
        <div class = "contactBox">
          <div class = "apptName">Fairway Manor</div>
          <div>2677 Dyer Ave</div>
          <div>New York, NY 10001</div>
          <div>(929) 263-5381</div>
        </div>
      </div>
    </div>
  </div>
  <div class = "separator separatorChicago"></div>
  <div id = "chicago" class="contentPannel contactPannel">
    <div class = "pageTitle"><p>Chicago</p></div>
    <div class = "contactBoxWrapper">
      <div class = "contactBoxContainer">
        <div class = "contactBox">
          <div class = "apptName">Primrose Heights</div>
          <div>4635 Ashland Ave</div>
          <div>Chicago IL, 60613</div>
          <div>(773) 585-7895</div>
        </div>
        <div class = "contactWhitespace"></div>
        <div class = "contactBox">
          <div class = "apptName">Bridgewater Park</div>
          <div>8917 Magnolia Ave</div>
          <div>Chicago IL 60640</div>
          <div>(773) 489-7892</div>
        </div>
        <div class = "contactWhitespace"></div>
        <div class = "contactBox">
          <div class = "apptName">Stoneridge Terrace</div>
          <div>1389 Edgewater Ave</div>
          <div>Chicago IL 60660</div>
          <div>(773) 895-1632</div>
        </div>
      </div>
    </div>
  </div>
  <div class = "separator separatorVegas"></div>
  <div id = "vegas" class="contentPannel contactPannel">
    <div class = "pageTitle"><p>Las Vegas</p></div>
    <div class = "contactBoxWrapper">
      <div class = "contactBoxContainer">
        <div class = "contactBox">
          <div class = "apptName">Sandy Ridge</div>
          <div>4891 Stewart Ave</div>
          <div>Las Vegas, NV 89101</div>
          <div>(702) 786-8735</div>
        </div>
        <div class = "contactWhitespace"></div>
        <div class = "contactBox">
          <div class = "apptName">Desert Estates</div>
          <div>2357 Valley View Blvd</div>
          <div>Las Vegas, NV 89119</div>
          <div>(702) 602-3942</div>
        </div>
        <div class = "contactWhitespace"></div>
        <div class = "contactBox">
          <div class = "apptName">Emerald Springs</div>
          <div>3572 Flamingo Rd</div>
          <div>Las Vegas, NV 89119</div>
          <div>(702) 894-3846</div>
        </div>
      </div>
    </div>
  </div>
  <div class = "separator separatorLosangeles"></div>
  <div id = "losAngeles" class="contentPannel contactPannel">
    <div class = "pageTitle"><p>Los Angeles</p></div>
    <div class = "contactBoxWrapper">
      <div class = "contactBoxContainer">
        <div class = "contactBox">
          <div class = "apptName">Fairview Villa</div>
          <div>7891 Palms Blvd</div>
          <div>Los Angeles, CA 90066</div>
          <div>(310) 689-2923</div>
        </div>
        <div class = "contactWhitespace"></div>
        <div class = "contactBox">
          <div class = "apptName">Broadview Terrace</div>
          <div>1678 Fox Hills Dr</div>
          <div>Los Angeles, CA 90230</div>
          <div>(310) 248-8762</div>
        </div>
        <div class = "contactWhitespace"></div>
        <div class = "contactBox">
          <div class = "apptName">Woodland Hills</div>
          <div>8943 Rose Ave</div>
          <div>Los Angeles, CA 90291</div>
          <div>(310) 924-2681</div>
        </div>
      </div>
    </div>
  </div>
<?php	include("templates/footer.php") ?>
