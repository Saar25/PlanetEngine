#include "/shaders/common/random/random"

// Vertex outputs
in vec2 v_position;

// Fragment outputs
layout (location = 0) out vec4 f_colour;

void main(void) {
    float random = random(v_position.xyxy);

    f_colour = vec4(vec3(random), 1);
}
