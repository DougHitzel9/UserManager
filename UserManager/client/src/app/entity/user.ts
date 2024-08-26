import { Role } from './role';

export class User {
    userId: number;
    username: string;
    role: Role;
    transaction: string;    // 'A' : Add, 'D' : Delete, 'E' : Edit

    constructor() {
        console.log('*** User()');
        this.role = new Role;
        this.transaction = 'A';
    }
}
