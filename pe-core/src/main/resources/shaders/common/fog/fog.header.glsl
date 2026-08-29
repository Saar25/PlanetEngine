/**
*
* Fog Header file
*
**/

struct Fog {
    vec3 color;
    float start;
    float end;
};

float calcFogAmount(float distance, Fog fog);

vec3 calcFogColor(float distance, Fog fog);

vec3 applyFogColor(float distance, Fog fog, vec3 color);