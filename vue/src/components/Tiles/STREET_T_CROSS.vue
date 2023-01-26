<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import * as THREE from "three";
import { GltfModel } from "troisjs";
import TRAFFIC_LIGHT from "@/components/objects/TRAFFIC_LIGHT.vue";
import { ObjectEnum } from "@/services/ObjectEnum";
import STREET_T_CROSS_URL from "@/assets/models/STREET_T_CROSS.glb?url";
import TRAFFIC_LIGHT_LIGHT from "@/components/objects/TRAFFIC_LIGHT_LIGHT.vue";
import { computed } from "vue";

const props = withDefaults(
  defineProps<{
    width: number;
    height: number;
    position: THREE.Vector3;
    rotation: THREE.Vector3;
    type: string;
    placedObject: any;
    orientation: string;
    trafficLightState: any;
  }>(),
  { width: 10, height: 10 }
);
const trafficLightLeft = "trafficLightLeft";
const trafficLightRight = "trafficLightRight";
const trafficLightStraight = "trafficLightStraight";

const trafficLight = computed(() => {
  if (props.trafficLightState == "NORTHSOUTH") {
    if (props.orientation == "WEST") {
      return {
        redOn1: false,
        redOn2: true,
        redOn3: false,
        greenOn1: true,
        greenOn2: false,
        greenOn3: true,
        yellowOn: false,
      };
    } else if (props.orientation == "EAST") {
      return {
        redOn1: false,
        redOn2: false,
        redOn3: true,
        greenOn1: true,
        greenOn2: true,
        greenOn3: false,
        yellowOn: false,
      };
    } else if (props.orientation == "NORTH") {
      return {
        redOn1: false,
        redOn2: true,
        redOn3: true,
        greenOn1: true,
        greenOn2: false,
        greenOn3: false,
        yellowOn: false,
      };
    } else if (props.orientation == "SOUTH") {
      return {
        redOn1: true,
        redOn2: false,
        redOn3: true,
        greenOn1: false,
        greenOn2: true,
        greenOn3: false,
        yellowOn: false,
      };
    }
  } else if (props.trafficLightState == "EASTWEST") {
    if (props.orientation == "WEST") {
      return {
        redOn1: true,
        redOn2: false,
        redOn3: true,
        greenOn1: false,
        greenOn2: true,
        greenOn3: false,
        yellowOn: false,
      };
    } else if (props.orientation == "EAST") {
      return {
        redOn1: true,
        redOn2: true,
        redOn3: false,
        greenOn1: false,
        greenOn2: false,
        greenOn3: true,
        yellowOn: false,
      };
    } else if (props.orientation == "NORTH") {
      return {
        redOn1: true,
        redOn2: false,
        redOn3: false,
        greenOn1: false,
        greenOn2: true,
        greenOn3: true,
        yellowOn: false,
      };
    } else if (props.orientation == "SOUTH") {
      return {
        redOn1: false,
        redOn2: true,
        redOn3: false,
        greenOn1: true,
        greenOn2: false,
        greenOn3: true,
        yellowOn: false,
      };
    }
  }
  return {
    redOn1: false,
    redOn2: false,
    redOn3: false,
    greenOn1: false,
    greenOn2: false,
    greenOn3: false,
    yellowOn: true,
  };
});

function setPosition(orientation: string, name: string): THREE.Vector3 {
  if (orientation == "WEST") {
    if (name == "trafficLightLeft") {
      return new THREE.Vector3(6.5, 0, 6.5);
    } else if (name == "trafficLightRight") {
      return new THREE.Vector3(6.5, 0, -6.5);
    } else if (name == "trafficLightStraight") {
      return new THREE.Vector3(-6.5, 0, -3);
    }
  } else if (orientation == "NORTH") {
    if (name == "trafficLightLeft") {
      return new THREE.Vector3(6.5, 0, 6.5);
    } else if (name == "trafficLightRight") {
      return new THREE.Vector3(3, 0, -6.5);
    } else if (name == "trafficLightStraight") {
      return new THREE.Vector3(-6.5, 0, 6.5);
    }
  } else if (orientation == "SOUTH") {
    if (name == "trafficLightLeft") {
      return new THREE.Vector3(-3, 0, 6.5);
    } else if (name == "trafficLightRight") {
      return new THREE.Vector3(-6.5, 0, -6.5);
    } else if (name == "trafficLightStraight") {
      return new THREE.Vector3(6.5, 0, -6.5);
    }
  } else if (orientation == "EAST") {
    if (name == "trafficLightLeft") {
      return new THREE.Vector3(6.5, 0, 3);
    } else if (name == "trafficLightRight") {
      return new THREE.Vector3(-6.5, 0, -6.5);
    } else if (name == "trafficLightStraight") {
      return new THREE.Vector3(-6.5, 0, 6.5);
    }
  }
  return new THREE.Vector3(0, 0, 0);
}

function setRotation(orientation: string, name: string): THREE.Vector3 {
  if (orientation == "WEST") {
    if (name == "trafficLightLeft") {
      return new THREE.Vector3(0, 0, 0);
    } else if (name == "trafficLightRight") {
      return new THREE.Vector3(0, 1.57, 0);
    } else if (name == "trafficLightStraight") {
      return new THREE.Vector3(0, 3.14, 0);
    }
  } else if (orientation == "NORTH") {
    if (name == "trafficLightLeft") {
      return new THREE.Vector3(0, 0, 0);
    } else if (name == "trafficLightRight") {
      return new THREE.Vector3(0, 1.57, 0);
    } else if (name == "trafficLightStraight") {
      return new THREE.Vector3(0, 4.71, 0);
    }
  } else if (orientation == "SOUTH") {
    if (name == "trafficLightLeft") {
      return new THREE.Vector3(0, 4.71, 0);
    } else if (name == "trafficLightRight") {
      return new THREE.Vector3(0, 3.14, 0);
    } else if (name == "trafficLightStraight") {
      return new THREE.Vector3(0, 1.75, 0);
    }
  } else if (orientation == "EAST") {
    if (name == "trafficLightLeft") {
      return new THREE.Vector3(0, 0, 0);
    } else if (name == "trafficLightRight") {
      return new THREE.Vector3(0, 3.14, 0);
    } else if (name == "trafficLightStraight") {
      return new THREE.Vector3(0, 4.71, 0);
    }
  }
  return new THREE.Vector3(0, 0, 0);
}
</script>
<template>
  <GltfModel
    ref="model"
    :src="STREET_T_CROSS_URL"
    :position="props.position"
    :rotation="props.rotation"
  />
  <TRAFFIC_LIGHT
    v-if:="props.placedObject === ObjectEnum.TRAFFIC_LIGHT"
    :position="
      props.position
        .clone()
        .add(setPosition(props.orientation, trafficLightLeft))
    "
    :rotation="setRotation(props.orientation, trafficLightLeft)"
    :type="ObjectEnum.TRAFFIC_LIGHT"
  />
  <TRAFFIC_LIGHT_LIGHT
    v-if:="props.placedObject === ObjectEnum.TRAFFIC_LIGHT"
    :position="
      props.position
        .clone()
        .add(setPosition(props.orientation, trafficLightLeft))
    "
    :angle="3"
    :red="trafficLight.redOn1"
    :yellow="trafficLight.yellowOn"
    :green="trafficLight.greenOn1"
  />
  <TRAFFIC_LIGHT
    v-if:="props.placedObject === ObjectEnum.TRAFFIC_LIGHT"
    :position="
      props.position
        .clone()
        .add(setPosition(props.orientation, trafficLightRight))
    "
    :rotation="setRotation(props.orientation, trafficLightRight)"
    :type="ObjectEnum.TRAFFIC_LIGHT"
  />
  <TRAFFIC_LIGHT_LIGHT
    v-if:="props.placedObject === ObjectEnum.TRAFFIC_LIGHT"
    :position="
      props.position
        .clone()
        .add(setPosition(props.orientation, trafficLightRight))
    "
    :angle="3"
    :red="trafficLight.redOn2"
    :yellow="trafficLight.yellowOn"
    :green="trafficLight.greenOn2"
  />
  <TRAFFIC_LIGHT
    v-if:="props.placedObject === ObjectEnum.TRAFFIC_LIGHT"
    :position="
      props.position
        .clone()
        .add(setPosition(props.orientation, trafficLightStraight))
    "
    :rotation="setRotation(props.orientation, trafficLightStraight)"
    :type="ObjectEnum.TRAFFIC_LIGHT"
  />
  <TRAFFIC_LIGHT_LIGHT
    v-if:="props.placedObject === ObjectEnum.TRAFFIC_LIGHT"
    :position="
      props.position
        .clone()
        .add(setPosition(props.orientation, trafficLightStraight))
    "
    :angle="3"
    :red="trafficLight.redOn3"
    :yellow="trafficLight.yellowOn"
    :green="trafficLight.greenOn3"
  />
</template>
