// Uniforms
uniform sampler2D u_atlas;
uniform ivec4 u_rayCastedFace;
uniform float u_glowTransition;

// Vertex outputs
flat in int v_id;
flat in int v_dir;
flat in int v_lit;
flat in vec3 v_normal;
flat in ivec3 v_block;
smooth in float v_ao;
in vec2 v_uvCoords;
in vec3 v_position;

// Fragment outputs
layout (location = 0) out vec4 f_colour;
layout (location = 1) out vec4 f_normalSpecular;

const float[] lights = float[]( .75, .75, 1.0, .25, .50, .50 );

void main(void) {
//    f_colour = vec4(colours[v_id], 1);
    f_colour = texture(u_atlas, v_uvCoords);
    if (f_colour.a < .5) discard;
    f_colour.rgb *= lights[v_dir];
    f_colour.rgb *= (v_lit + 5) / 20.0;
    f_colour.rgb *= 1 - pow(v_ao * .9, 4);

    if (v_block == u_rayCastedFace.xyz && v_dir == u_rayCastedFace.w) {
        vec2 facePosition;
        if (v_dir == 0 || v_dir == 1) {
            facePosition = v_position.yz - v_block.yz;
        } else if (v_dir == 2 || v_dir == 3) {
            facePosition = v_position.xz - v_block.xz;
        } else {
            facePosition = v_position.xy - v_block.xy;
        }
        float borderDistance = 1 - max(abs(facePosition.x - .5), abs(facePosition.y - .5)) * 2;

        f_colour.rgb *= smoothstep(borderDistance, 0, .02);
    }

    f_normalSpecular = vec4(v_normal, 1);
}
