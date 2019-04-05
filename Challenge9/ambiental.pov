#include "colors.inc"

plane {
	y, -2
	pigment {hexagon Black, White, Red}	
}

sphere {
    <-3,0,0>, 1
    pigment { Green }
    finish {
      ambient 0.5
      diffuse 0.2
      phong 0.80
      phong_size 25
      reflection 0.75
    }
}

sphere{
    <2,3.5,0>,1
    pigment{ White }
    finish{
      ambient 0.1
      diffuse 0.1
      phong 100
      phong_size 75
      reflection 0.1
    }
}

sphere{
    <-2,3.5,0>,1
    pigment{ Pink }
    finish{
      ambient 0.1
      diffuse 0.1
      phong 50
      phong_size 10
      reflection 0.5
    }
}

sphere {
    <0,0,0>, 1
    pigment { Red }
    finish {
      ambient 0.2
      diffuse 0.6
      phong .75
      phong_size 100
    }
}

sphere {
    <3,0,0>, 1
    pigment { Blue }
    finish {
      ambient 0.8
      diffuse 0.3
      phong .75
      phong_size 1000
    }
}


  light_source {
    <10, 10, -10>
    color Yellow
  }
  
  
  light_source{
    <10,10,10>
    color Blue
  }

camera {
	location <-5, 5, -10>
	angle 90
	look_at <0, 0, 0>	
}