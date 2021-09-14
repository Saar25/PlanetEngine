// Definitions
#ifndef COMPONENTS
#define COMPONENTS 4
#endif

// Vertex outputs
in vec2 v_position;

// Uniforms
uniform sampler2D u_texture;
uniform sampler2D u_multiply;

// Fragment outputs
layout (location = 0) out vec4 f_colour;

void main(void) {
    vec4 colour = texture(u_texture, v_position);
    vec4 multiply = texture(u_multiply, v_position);
    
    #if COMPONENTS == 4
    f_colour = colour * multiply.rgba;
    #elif COMPONENTS == 3
    f_colour = colour * vec4(multiply.rgb, 1);
    #elif COMPONENTS == 2
    f_colour = colour * vec4(multiply.rg, 1, 1);
    #elif COMPONENTS == 1
    f_colour = colour * vec4(multiply.rrr, 1);
    #endif
}
