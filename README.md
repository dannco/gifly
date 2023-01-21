# gifly
For generating various gif particle animations.  
Kind of bare-bones as far as projects go. Essentially giving particles instructions to move through space for a number of frames, and then make a gif animation out of those frames.  
Image can be instructed to have the particle trails fade or "smoke", as illustrated in the examples.  

All particle types are derived from a base abstract class that has information about size, visibility, position (0,0 being top left corner), and color.  
Types:  
- LineParticle: moves every frame according to X and Y vectors (1x,1y moves diagonally down and right, for example) until it moves out of view.
- BouncingLineParticle: same movement as LineParticle but should "bounce" by having its vectors change direction to move inward if it moves out of view.
- WrappingLineParticle: same movement as LineParticle but wraps around the image to show up on the opposite side if out of view
- PathParticle: moves between set coordinate pairs over the course of a loop with a declared number of frames.
- CyclicalParticle: Moves in X and Y according to functions of their "progress" through a loop.
  - For example if the function of Y is defined as M * sin(p * 2&pi;) where `p` is the "progress" parameter as a percentage of loop completion, the particle will move between +M and -M from its starting Y position. 
  
TODOs:
- Some kind of interface that declares what components to add to the gif-animation, e.g. Json files with declaration of images and/or particles.
- Enable animation using components like JPEG images, sprite sheets.
- Isolate the pixels produced by each particle/component so that they can fade or smoke or whatever.
- Integrate with GPU
