import { Role } from './role';

export class User {
    userId: number;
    username: string;
    role: Role;

    constructor() {
        console.log('*** User()');
        this.role = new Role;
    }
}
