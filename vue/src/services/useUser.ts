/* eslint-disable prettier/prettier */
import json from './useUserTest.json';
import { reactive, readonly } from "vue";
export function useUser(): any {

    interface IUserList {
        users: string[]
    }

    const userListState:IUserList = reactive<IUserList> ({
        users: []
    });

    async function getUserList(instanceName: string) {
        try {
            //code preperation for REST-Call
            /*
            const controller = new AbortController();
            const URL = 'useUserTest.json';
    
            const id = setTimeout(() => controller.abort(), 8000);
    
            const response = await fetch(URL, {
                method: 'GET',
                signal: controller.signal
            });
            
            const jsonData: IUserList = await response.json();

            clearTimeout(id);
    
            console.log(response.text());
            */
            const jsonData : IUserList = json
            userListState.users = jsonData.users
        } catch(reason) {
            console.log(`ERROR: Fetching Map failed: ${reason}`);
        }
        
    }

    return {
        userList: readonly(userListState),
        getUserList
    }
}
