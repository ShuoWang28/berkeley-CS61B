public class Planet{
  public double xxPos;
  public double yyPos;
  public double xxVel;
  public double yyVel;
  public double mass;
  public String imgFileName;
  public static final double G = 6.67e-11;

  //*Intialize an instance of a Planet class*/
  public Planet(double xP, double yP, double xV, double yV,
   double m, String img){
     this.xxPos = xP;
     this.yyPos = yP;
     this.xxVel = xV;
     this.yyVel = yV;
     this.mass = m;
     this.imgFileName = img;
  }

  /** take in a Planet object and initialize an identical Planet object (i.e. a copy) */
  public Planet(Planet p){
    this.xxPos = p.xxPos;
    this.yyPos = p.yyPos;
    this.xxVel = p.xxVel;
    this.yyVel = p.yyVel;
    this.mass = p.mass;
    this.imgFileName = p.imgFileName;
  }

  /** Calculates the distance between two Planets. */
  public double calcDistance(Planet p){
    double dx = this.xxPos - p.xxPos;
    double dy = this.yyPos - p.yyPos;
    double distance = Math.sqrt(dx * dx + dy * dy);
    return distance;
  }

  /** The calcForceExertedBy method takes in a planet, and
    * returns a double describing the force exerted on this planet by the given planet.
    */
  public double calcForceExertedBy(Planet p){
    double r = this.calcDistance(p);
    double F = (G * this.mass * p.mass) / (r*r);
    return F;
  }
  /** describe the force exerted in the X and Y directions, respectively.*/
  public double calcForceExertedByX(Planet p){
    double F = this.calcForceExertedBy(p);
    double r = this.calcDistance(p);
    double dx = p.xxPos - this.xxPos;
    double Fx = F * dx / r;
    return Fx;
  }

  public double calcForceExertedByY(Planet p){
    double F = this.calcForceExertedBy(p);
    double r = this.calcDistance(p);
    double dy = p.yyPos - this.yyPos;
    double Fy = F * dy / r;
    return Fy;
  }

  /** each take in an array of Planets and calculate the net X and net Y force
   exerted by all planets in that array upon the current Planet. */
  public double calcNetForceExertedByX(Planet[] pArr){
    int i = 0;
    double FxNet = 0;
    while(i < pArr.length){
      if(this.equals(pArr[i])){
        i++;
        continue;
      }
      FxNet = FxNet + this.calcForceExertedByX(pArr[i]);
      i++;
    }
    return FxNet;
  }

  public double calcNetForceExertedByY(Planet[] pArr){
    int i = 0;
    double FyNet = 0;
    while(i < pArr.length){
      if(this.equals(pArr[i])){
        i++;
        continue;
      }
      FyNet = FyNet + this.calcForceExertedByY(pArr[i]);
      i++;
    }
    return FyNet;
  }

  /** update the planet’s position and velocity instance variables */
  public void update(double dt, double fX, double fY){
    double ax = fX / this.mass;
    double ay = fY / this.mass;
    this.xxVel = this.xxVel + dt * ax;
    this.yyVel = this.yyVel + dt * ay;
    this.xxPos = this.xxPos + dt * this.xxVel;
    this.yyPos = this.yyPos + dt * this.yyVel;
  }
  /** uses the StdDraw API to draw the Planet’s image at the Planet’s position. */
  public void draw(){
    StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
  }

}
