import { Category } from "./category.model"
import { User } from "./user"

export interface Item {
    id: number
    name: string
    description: string
    photo: string
    category: Category
    available: boolean
    owner: User
}