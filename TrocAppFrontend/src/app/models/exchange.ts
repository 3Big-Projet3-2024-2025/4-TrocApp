import { User } from "./user"

export interface Exchange {
    id_exchange: number
    offeredObjectId: number
    requestedObjectId: number
    proposalDate: string
    acceptanceDate: string
    status: string
    firstEvaluation: number
    secondEvaluation: number
    initiator: User
    receiver: User
}