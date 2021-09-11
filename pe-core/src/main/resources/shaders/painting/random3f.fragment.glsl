#include "/shaders/common/random/random"

// Vertex outputs
in vec2 v_position;

// Fragment outputs
layout (location = 0) out vec4 f_colour;

void main(void) {
    float x = random(v_position.xy);
    float y = random(v_position.xy * x * 156.562);
    float z = random(v_position.xy * y * 913.874);
    
    f_colour = vec4(x, y, z, 1);
}
