/**
*
* Fog Header file
*
**/

float calcFogAmount(float distance, Fog fog);

vec3 calcFogColour(float distance, Fog fog);

vec3 applyFogColour(float distance, Fog fog, vec3 colour);